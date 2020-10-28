package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.api.model.trakt.SeasonsItem
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalTime::class,
    ExperimentalStdlibApi::class
)
class Seasons(private val roomDb: RoomDb, private val traktApi: TraktApi) {

    val seasonsStore =
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
}
