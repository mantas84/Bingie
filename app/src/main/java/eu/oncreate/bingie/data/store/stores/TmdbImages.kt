package eu.oncreate.bingie.data.store.stores

import com.dropbox.android.external.store4.Fetcher
import com.dropbox.android.external.store4.SourceOfTruth
import com.dropbox.android.external.store4.StoreBuilder
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.model.tmdb.TmdbImages
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
class TmdbImages(private val roomDb: RoomDb, private val tmdbApi: TmdbApi) {

    val tmdbImagesStore =
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
}
