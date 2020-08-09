package eu.oncreate.bingie.data

import eu.oncreate.bingie.BuildConfig
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.tmdb.Configuration
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import eu.oncreate.bingie.fragment.list.ShowWithImages
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

class Datasource @Inject constructor(
    private val local: RoomDb,
    private val fanartApi: FanartApi,
    private val tmdbApi: TmdbApi,
    private val traktApi: TraktApi
) {

    private var configuration: Configuration? = null

    private val configurationSingle = local.configurationDao()
        .getConfiguration()
        .flatMap { localConfiguration ->
            val now = Instant.now().epochSecond
            val interval = 3600 * 24 * 3
            val configurationTime = localConfiguration.firstOrNull()?.timestamp ?: 0L
            Timber.d("Configuration $configurationTime")
            if (configurationTime + interval > now) {
                Single.just(localConfiguration.first())
            } else {
                tmdbApi.getConfiguration(BuildConfig.TMDB_TOKEN).map { getLocal(it) }
            }
        }.doOnSuccess {
            local.configurationDao().insertConfiguration(it)
            configuration = it
        }

    fun search(query: String): Single<List<SearchResultItem>> {
        return traktApi
            .search(query)
            .doOnError { Timber.d("error here $it") }
            .flatMap { remoteList ->
                Single.just(remoteList.map { SearchResultItem.toLocal(it) })
            }
            .onErrorResumeNext(local.searchResultItemDao().searchSearchResultItem(query))
    }

    fun getImages(item: SearchResultItem): Single<ShowWithImages> {

        val configurationSingle = if (configuration != null) {
            Single.just(configuration!!)
        } else {
            this.configurationSingle
        }

        return Single.zip(
            getTmdbImages(item.show.ids.tmdb),
            getFanartPicture(item.show.ids.tvdb),
            configurationSingle,
            Function3 { tmdbImage: eu.oncreate.bingie.data.local.model.tmdb.TmdbImages, fanartImage: eu.oncreate.bingie.data.local.model.fanart.FanartImages, configuration: Configuration ->
                ShowWithImages(
                    item,
                    tmdbImage.takeUnless { it.id == -1 },
                    configuration.images,
                    fanartImage.takeUnless { it.thetvdbId == null }
                )
            })
    }

    private fun getFanartPicture(tvdbId: Int?): Single<eu.oncreate.bingie.data.local.model.fanart.FanartImages> {

        val fanartFallback = eu.oncreate.bingie.data.local.model.fanart.FanartImages(
            "-1",
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        return when (tvdbId) {
            null -> Single.just(fanartFallback)
            else ->
                local.fanartImageDao()
                    .getFanart(tvdbId.toString())
                    .flatMap { localFanartList ->
                        if (localFanartList.isNotEmpty()) {
                            Single.just(localFanartList.first())
                        } else {
                            (fanartApi
                                .getImages(tvdbId, BuildConfig.FANART_KEY)
                                .flatMapCompletable {
                                    local.fanartImageDao().insertFanart(
                                        eu.oncreate.bingie.data.local.model.fanart.FanartImages.toLocal(
                                            it
                                        )
                                    )
                                }
                                .andThen(
                                    local.fanartImageDao().getFanart(tvdbId.toString())
                                        .map { it.firstOrNull() }))
                                .onErrorReturnItem(fanartFallback)
                        }
                    }
        }
    }

    private fun getTmdbImages(tmdbId: Int?): Single<eu.oncreate.bingie.data.local.model.tmdb.TmdbImages> {
        val tmdbFallback = eu.oncreate.bingie.data.local.model.tmdb.TmdbImages(
            backdrops = emptyList(),
            id = -1,
            posters = emptyList()
        )

        return when (tmdbId) {
            null -> Single.just(tmdbFallback)
            else -> local.tmdbImagesDao()
                .getTmdbImages(tmdbId.toString())
                .flatMap { localImages ->
                    if (localImages.isNotEmpty()) {
                        Single.just(localImages.first())
                    } else {
                        (tmdbApi
                            .getImages(tmdbId, BuildConfig.TMDB_TOKEN)
                            .flatMapCompletable {
                                local.tmdbImagesDao().insertTmdbImages(
                                    eu.oncreate.bingie.data.local.model.tmdb.TmdbImages.toLocal(it)
                                )
                            }
                            .andThen(
                                local.tmdbImagesDao().getTmdbImages(tmdbId.toString())
                                    .map { it.firstOrNull() }))
                            .onErrorReturnItem(tmdbFallback)
                    }
                }
        }
    }

    fun getSeasons(traktId: Int): Single<List<SeasonsItem>> {
        return local
            .seasonsItemDao()
            .getSeasonsItem(traktId)
            .flatMap {
                if (it.isNotEmpty()) {
                    Timber.d("SEASONS not empty")
                    it.forEach { Timber.d("SEASONS $it") }
                    Single.just(it)
                } else {
                    traktApi
                        .showSeasons(traktId).flatMap { seasons ->
                            Timber.d("SEASONS from api ${seasons.size}")
                            seasons.forEach { Timber.d("SEASONS $it") }
                            local.seasonsItemDao()
                                .insertAllSeasonsItems(seasons.map { getLocal(it,traktId) }).doOnError { Timber.d("SEASONS error $it") }
                                .andThen(local.seasonsItemDao().getSeasonsItem(traktId)).doOnSuccess { Timber.d("SEASONS count ${it.size}") }
                        }
                }
            }
    }

    fun getShow(traktId: String): Single<SearchResultItem> {
        return local
            .searchResultItemDao()
            .getSearchResultItem(traktId)
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it.first())
                } else {
                    traktApi.searchLookUp(traktId).flatMap {
                        local
                            .searchResultItemDao()
                            .insertAllSearchResultItem(it.map {
                                SearchResultItem.toLocal(it)
                            })
                            .andThen(
                                local.searchResultItemDao().getSearchResultItem(traktId)
                                    .map { it.first() }
                            )
                    }
                }
            }
    }
}
