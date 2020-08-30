package eu.oncreate.bingie.data.store

import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import com.dropbox.android.external.store4.nonFlowValueFetcher
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.api.model.SeasonsItem
import eu.oncreate.bingie.data.api.model.fanart.FanartImages
import eu.oncreate.bingie.data.api.model.tmdb.Configuration
import eu.oncreate.bingie.data.api.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import kotlin.time.ExperimentalTime

@OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalTime::class,
    ExperimentalStdlibApi::class
)
class Stores(
    private val roomDb: RoomDb,
    private val fanartApi: FanartApi,
    private val tmdbApi: TmdbApi,
    private val traktApi: TraktApi
) {

    val configuration =
        StoreBuilder
            .from<Unit, Configuration, eu.oncreate.bingie.data.local.model.tmdb.Configuration>(
                fetcher = nonFlowValueFetcher { tmdbApi.getConfiguration() },
                sourceOfTruth = SourceOfTruth.fromNonFlow(
                    reader = {
                        roomDb.configurationDao().getConfiguration().firstOrNull()
                    },
                    writer = { _, item ->
                        roomDb.configurationDao().insertConfiguration((getLocal(item)))
                    },
                    delete = {
                        roomDb.configurationDao()::deleteConfiguration
                    },
                    deleteAll = roomDb.configurationDao()::deleteConfiguration
                )
            )
            .build()

    val fanart =
        StoreBuilder
            .from<Int, FanartImages, eu.oncreate.bingie.data.local.model.fanart.FanartImages>(
                fetcher = nonFlowValueFetcher { fanartApi.getImages(it) },
                sourceOfTruth = SourceOfTruth.fromNonFlow(
                    reader = {
                        roomDb.fanartImageDao().getFanart(it).firstOrNull()
                    },
                    writer = { _, item ->
                        roomDb.fanartImageDao()
                            .insertFanart(
                                eu.oncreate.bingie.data.local.model.fanart.FanartImages.toLocal(
                                    item
                                )
                            )
                    },
                    delete = {
                        roomDb.fanartImageDao().deleteFanart(it)
                    },
                    deleteAll = roomDb.fanartImageDao()::deleteAllFanarts
                )
            )
            .build()

    val seasons =
        StoreBuilder
            .from<Int, List<SeasonsItem>, List<eu.oncreate.bingie.data.local.model.trakt.SeasonsItem>>(
                fetcher = nonFlowValueFetcher { traktApi.showSeasons(it) },
                sourceOfTruth = SourceOfTruth.fromNonFlow(
                    reader = {
                        roomDb.seasonsItemDao().getSeasonsItem(it).ifEmpty { null }
                    },
                    writer = { seriesId, items ->
                        roomDb.seasonsItemDao()
                            .insertAllSeasonsItems(items.map { getLocal(it, seriesId) })
                    },
                    delete = {
                        roomDb.seasonsItemDao().deleteSeasonsItem(it)
                    },
                    deleteAll = roomDb.seasonsItemDao()::deleteAllSeasonsItem
                )
            )
            .build()

    val tmdbImages =
        StoreBuilder
            .from<Int, TmdbImages, eu.oncreate.bingie.data.local.model.tmdb.TmdbImages>(
                fetcher = nonFlowValueFetcher { tmdbApi.getImages(it) },
                sourceOfTruth = SourceOfTruth.fromNonFlow(
                    reader = {
                        roomDb.tmdbImagesDao().getTmdbImages(it).firstOrNull()
                    },
                    writer = { _, item ->
                        roomDb.tmdbImagesDao()
                            .insertTmdbImages(
                                eu.oncreate.bingie.data.local.model.tmdb.TmdbImages.toLocal(
                                    item
                                )
                            )
                    },
                    delete = {
                        roomDb.tmdbImagesDao().deleteTmdbImage(it)
                    },
                    deleteAll = roomDb.tmdbImagesDao()::deleteAllTmdbImages
                )
            )
            .build()

    val search: Store<Pair<String, Int>, List<SearchResultItem>> =

        StoreBuilder
            .from<Pair<String, Int>, List<eu.oncreate.bingie.data.api.model.SearchResultItem>, List<SearchResultItem>>(
                fetcher = nonFlowValueFetcher { (query, page) -> traktApi.search(query) },
                sourceOfTruth = SourceOfTruth.fromNonFlow(
                    reader = { (query, _) ->
                        if (query.isEmpty()) {
                            roomDb.searchResultItemDao().searchSearchResultItem()
                        } else {
                            roomDb.searchResultItemDao().searchSearchResultItem(query)
                        }
                    },
                    writer = { (_, _), items ->
                        Timber.d("writer count ${items.size}")
                        roomDb.searchResultItemDao()
                            .insertAllSearchResultItem((items.map { getLocal(it) }))
                    },
                    delete = { (_, id) ->
                        roomDb.searchResultItemDao().deleteSearchResultItem(id)
                    },
                    deleteAll = roomDb.searchResultItemDao()::deleteAllSearchResultItems
                )
            )
            .build()

    val showStore: Store<Int, List<SearchResultItem>> =

        StoreBuilder
            .from<Int, List<eu.oncreate.bingie.data.api.model.SearchResultItem>, List<SearchResultItem>>(
                fetcher = nonFlowValueFetcher { traktId -> traktApi.searchLookUp(traktId) },
                sourceOfTruth = SourceOfTruth.fromNonFlow(
                    reader = { traktId ->
                        roomDb.searchResultItemDao().getSearchResultItem(traktId)
                    },
                    writer = { traktId, items ->
                        roomDb.searchResultItemDao()
                            .insertAllSearchResultItem((items.map { getLocal(it) }))
                    },
                    delete = { traktId ->
                        roomDb.searchResultItemDao().deleteSearchResultItem(traktId)
                    },
                    deleteAll = roomDb.searchResultItemDao()::deleteAllSearchResultItems
                )
            )
            .build()
}
