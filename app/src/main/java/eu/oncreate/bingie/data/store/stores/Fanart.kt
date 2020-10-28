package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.RoomDb
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalTime::class,
    ExperimentalStdlibApi::class
)
class Fanart(private val roomDb: RoomDb, private val fanartApi: FanartApi) {

    val fanartStore =
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
}
