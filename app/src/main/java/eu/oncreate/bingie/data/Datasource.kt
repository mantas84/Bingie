package eu.oncreate.bingie.data

import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.LocalSource
import eu.oncreate.bingie.data.local.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.tmdb.Configuration
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import eu.oncreate.bingie.fragment.list.ShowWithImages
import io.reactivex.Single
import io.reactivex.functions.Function3
import java.time.Instant
import javax.inject.Inject

class Datasource @Inject constructor(
    private val local: LocalSource,
    private val fanartApi: FanartApi,
    private val tmdbApi: TmdbApi,
    private val traktApi: TraktApi
) {

    private var configuration: Configuration? = null

    private val configurationSingle = local.getConfiguration()
        .flatMap { localConfiguration ->
            val now = Instant.now().epochSecond
            val interval = 3600 * 24 * 3
            val configurationTime = localConfiguration.firstOrNull()?.timestamp ?: 0L
            if (configurationTime + interval > now) {
                Single.just(localConfiguration.first())
            } else {
                tmdbApi.getConfiguration().map { getLocal(it) }
            }
        }.doOnSuccess {
            local.insertConfiguration(it)
            configuration = it
        }

    fun search(query: String): Single<List<SearchResultItem>> {
        return traktApi
            .search(query)
            .flatMap { remoteList ->
                Single.just(remoteList.map { SearchResultItem.toLocal(it) })
            }
            .onErrorResumeNext(local.searchItems(query))
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
            Function3 { tmdbImage: TmdbImages, fanartImage: FanartImages, configuration: Configuration ->
                ShowWithImages(
                    item,
                    tmdbImage.takeUnless { it == TmdbImages.EMPTY },
                    configuration.images,
                    fanartImage.takeUnless { it == FanartImages.EMPTY }
                )
            })
    }

    private fun getFanartPicture(tvdbId: Int?): Single<FanartImages> {

        val fanartFallback = FanartImages.EMPTY

        return when (tvdbId) {
            null -> Single.just(fanartFallback)
            else ->
                local
                    .getFanart(tvdbId)
                    .flatMap { localFanartList ->
                        if (localFanartList.isNotEmpty()) {
                            Single.just(localFanartList.first())
                        } else {
                            (fanartApi
                                .getImages(tvdbId)
                                .flatMapCompletable {
                                    local.insertFanart(it)
                                }
                                .andThen(
                                    local.getFanart(tvdbId)
                                        .map { it.firstOrNull() }))
                                .onErrorReturnItem(fanartFallback)
                        }
                    }
        }
    }

    private fun getTmdbImages(tmdbId: Int?): Single<TmdbImages> {

        val tmdbFallback = TmdbImages.EMPTY

        return when (tmdbId) {
            null -> Single.just(tmdbFallback)
            else -> local
                .getTmdbImages(tmdbId)
                .flatMap { localImages ->
                    if (localImages.isNotEmpty()) {
                        Single.just(localImages.first())
                    } else {
                        (tmdbApi
                            .getImages(tmdbId)
                            .flatMapCompletable {
                                local.insertTmdbImages(it)
                            }
                            .andThen(
                                local.getTmdbImages(tmdbId)
                                    .map { it.firstOrNull() }))
                            .onErrorReturnItem(tmdbFallback)
                    }
                }
        }
    }

    fun getSeasons(traktId: Int): Single<List<SeasonsItem>> {
        return local
            .getSeasons(traktId)
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it)
                } else {
                    traktApi
                        .showSeasons(traktId).flatMap { seasons ->
                            local
                                .insertSeasons(seasons, traktId)
                                .andThen(local.getSeasons(traktId))
                        }
                }
            }
    }

    fun getShow(traktId: Int): Single<SearchResultItem> {
        return local.getShow(traktId)
            .flatMap {
                if (it.isNotEmpty()) {
                    Single.just(it.first())
                } else {
                    traktApi
                        .searchLookUp(traktId)
                        .flatMap {
                            local.insertShow(it)
                                .andThen(local.getShow(traktId).map { it.first() })
                        }
                }
            }
    }
}
