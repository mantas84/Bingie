package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.Store
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalTime::class,
    ExperimentalStdlibApi::class
)
class Shows(private val roomDb: RoomDb, private val traktApi: TraktApi) {

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
