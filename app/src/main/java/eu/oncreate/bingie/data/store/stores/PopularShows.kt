package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.PagedResponse
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.api.model.trakt.PopularShow
import eu.oncreate.bingie.data.local.RoomDb
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
class PopularShows(private val roomDb: RoomDb, private val traktApi: TraktApi) {

    val popularShowsStore: Store<Pair<Int, Int>, PagedResponse<List<SearchResultItem>>> =
        StoreBuilder
            .from<
                    Pair<Int, Int>,
                    PagedResponse<List<PopularShow>>,
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
                            .map {
                                eu.oncreate.bingie.data.local.model.trakt.PopularShow.toSearchItem(
                                    it
                                )
                            }
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
                                .insertAllPopularShow(items.map {
                                    eu.oncreate.bingie.data.local.model.trakt.PopularShow.toLocal(
                                        it
                                    )
                                })

                            roomDb.searchResultItemDao()
                                .insertAllSearchResultItem((items.map {
                                    eu.oncreate.bingie.data.local.model.trakt.PopularShow.toSearchItem(
                                        eu.oncreate.bingie.data.local.model.trakt.PopularShow.toLocal(
                                            it
                                        )
                                    )
                                }))
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
}
