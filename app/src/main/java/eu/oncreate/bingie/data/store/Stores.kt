package eu.oncreate.bingie.data.store

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.PagedResponse
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.api.model.fanart.FanartImages
import eu.oncreate.bingie.data.api.model.tmdb.Configuration
import eu.oncreate.bingie.data.api.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.api.model.trakt.SeasonsItem
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.trakt.PopularShow.Companion.toLocal
import eu.oncreate.bingie.data.local.model.trakt.PopularShow.Companion.toSearchItem
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.TotalShows
import eu.oncreate.bingie.utils.toPaged
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
                fetcher = Fetcher.of { tmdbApi.getConfiguration() },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader = {
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
                fetcher = Fetcher.of { fanartApi.getImages(it) },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader = {
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
                fetcher = Fetcher.of { traktApi.showSeasons(it) },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader = {
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
                fetcher = Fetcher.of { tmdbApi.getImages(it) },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader = {
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

    val search: Store<Triple<String, Int, Int>, PagedResponse<List<SearchResultItem>>> =

        StoreBuilder
            .from<
                    Triple<String, Int, Int>,
                    PagedResponse<List<eu.oncreate.bingie.data.api.model.trakt.SearchResultItem>>,
                    PagedResponse<List<SearchResultItem>>>(
                fetcher = Fetcher.of { (query, page, perPage) ->
                    traktApi.search(
                        query = query,
                        page = page,
                        limit = perPage.toString()
                    ).toPaged()
                },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader = { (query, page, perPage) ->
                        val from = (page - 1) * perPage
                        val to = (page) * perPage
                        val items = roomDb.searchResultItemDao()
                            .searchSearchResultItem(query)

                        val content = items.subList(from, Math.min(items.size, to))
                        PagedResponse(
                            content = content,
                            page = page,
                            limit = perPage,
                            totalPages = items.size.minus(1).div(perPage) + 1,
                            totalItems = items.size,
                        )
                    },
                    writer = { (query, page, perPage), result ->
                        val items = result.content
                        if (items != null) {
                            roomDb.searchResultItemDao()
                                .insertAllSearchResultItem((items.map { getLocal(it) }))
                        } else {
                            // todo
                            Timber.d("Error here ${result.error}")
                        }
                    },
                    delete = { (query, page, perPage) ->
                        val from = (page - 1) * perPage
                        val to = (page) * perPage
                        val items = roomDb.searchResultItemDao()
                            .searchSearchResultItem(query)
                            .subList(from, to)
                        items.forEach {
                            roomDb.searchResultItemDao().deleteSearchResultItem(it.parentId)
                        }
                    },
                    deleteAll = roomDb.searchResultItemDao()::deleteAllSearchResultItems
                )
            )
            .build()

    val popularShows: Store<Pair<Int, Int>, PagedResponse<List<SearchResultItem>>> =
        StoreBuilder
            .from<
                    Pair<Int, Int>,
                    PagedResponse<List<eu.oncreate.bingie.data.api.model.trakt.PopularShow>>,
                    PagedResponse<List<SearchResultItem>>>(
                fetcher = Fetcher.of { (page, perPage) ->
                    traktApi.getPopular(
                        page = page,
                        limit = perPage.toString()
                    ).toPaged()
                },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader = { (page, perPage) ->
                        val from = (page - 1) * perPage
                        val to = (page) * perPage
                        val items = roomDb.popularShowsDao()
                            .searchPopularShow()
                        val content = items
                            .subList(from, Math.min(items.size, to))
                            .map { toSearchItem(it) }
                        val totalItems = roomDb
                            .totalShowsDao()
                            .getAll()
                            .lastOrNull()
                            ?.totalShows ?: 10000
                        val totalPages = totalItems.div(perPage)
                        Timber.d("&page= total pages = $totalPages, size ${items.size}")
                        PagedResponse(
                            content = content,
                            page = page,
                            limit = perPage,
                            totalPages = totalPages,
                            totalItems = totalItems,
                        )
                    },
                    writer = { (page, perPage), result ->
                        val items = result.content
                        if (items != null) {
                            if (page == 1) {
                                roomDb.popularShowsDao().deleteAllPopularShows()
                                roomDb.totalShowsDao().deleteAll()
                                roomDb.totalShowsDao()
                                    .insert(TotalShows(totalShows = result.totalItems ?: 0))
                            }
                            roomDb.popularShowsDao()
                                .insertAllPopularShow(items.map { toLocal(it) })

                            roomDb.searchResultItemDao()
                                .insertAllSearchResultItem((items.map { toSearchItem(toLocal(it)) }))
                        } else {
                            // todo
                            Timber.d("Error here ${result.error}")
                        }
                    },
                    delete = { (page, perPage) ->
                        val from = (page - 1) * perPage
                        val to = (page) * perPage
                        val items = roomDb.popularShowsDao()
                            .searchPopularShow()
                            .subList(from, to)
                        items.forEach {
                            roomDb.searchResultItemDao().deleteSearchResultItem(it.parentId)
                        }
                    },
                    deleteAll = roomDb.popularShowsDao()::deleteAllPopularShows
                )
            ).build()

    val showStore: Store<Int, List<SearchResultItem>> =

        StoreBuilder
            .from<Int, List<eu.oncreate.bingie.data.api.model.trakt.SearchResultItem>, List<SearchResultItem>>(
                fetcher = Fetcher.of
                { traktId -> traktApi.searchLookUp(traktId) },
                sourceOfTruth = SourceOfTruth.of(
                    nonFlowReader =
                    { traktId ->
                        roomDb.searchResultItemDao().getSearchResultItem(traktId)
                    },
                    writer =
                    { traktId, items ->
                        roomDb.searchResultItemDao()
                            .insertAllSearchResultItem((items.map { getLocal(it) }))
                    },
                    delete =
                    { traktId ->
                        roomDb.searchResultItemDao().deleteSearchResultItem(traktId)
                    },
                    deleteAll = roomDb.searchResultItemDao()::deleteAllSearchResultItems
                )
            )
            .build()
}
