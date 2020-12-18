package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.PagedResponse
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
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
class Search(private val roomDb: RoomDb, private val traktApi: TraktApi) {

    val searchStore: Store<Triple<String, Int, Int>, PagedResponse<List<SearchResultItem>>> =

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
                    writer = { (_, _, _), result ->
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
}
