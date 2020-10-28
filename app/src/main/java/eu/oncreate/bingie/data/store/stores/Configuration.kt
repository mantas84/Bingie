package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.model.tmdb.Configuration
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
class Configuration(private val roomDb: RoomDb, private val tmdbApi: TmdbApi) {

    val configurationStore =
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
}
