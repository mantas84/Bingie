package eu.oncreate.bingie.data.store

import com.dropbox.android.external.store4.fresh
import com.dropbox.android.external.store4.get
import eu.oncreate.bingie.data.PagedResponse
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.local.model.tmdb.Images
import eu.oncreate.bingie.fragment.list.ShowWithImages
import kotlinx.coroutines.runBlocking
import java.time.Instant
import javax.inject.Inject
import eu.oncreate.bingie.data.local.model.tmdb.Configuration as LocalConfiguration
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem as LocalSearchResult
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem as LocalSeasonsItem

class StoreSource @Inject constructor(
    roomDb: RoomDb,
    fanartApi: FanartApi,
    tmdbApi: TmdbApi,
    traktApi: TraktApi
) {

    private val stores = Stores(roomDb, fanartApi, tmdbApi, traktApi)
    private val storeImagesHelper = StoreImages(stores.tmdbImages, stores.fanart)

    private val configuration: LocalConfiguration by lazy {
        runBlocking {
            val localConfiguration = stores.configuration.get(Unit)
            val now = Instant.now().epochSecond
            val interval = 3600 * 24 * 3
            val configurationTime = localConfiguration.timestamp
            return@runBlocking if (configurationTime + interval > now) {
                localConfiguration
            } else {
                stores.configuration.fresh(Unit)
            }
        }
    }

    suspend fun search(
        query: String,
        limit: Int,
        page: Int
    ): PagedResponse<List<eu.oncreate.bingie.data.local.model.trakt.SearchResultItem>> {
        return if (query.isEmpty()) {
            stores.popularShows.fresh(Pair(page, limit))
        } else {
            stores.search.fresh(Triple(query, page, limit))
        }
    }

    suspend fun getImages(
        items: List<eu.oncreate.bingie.data.local.model.trakt.SearchResultItem>,
        forceUpdate: Boolean = false,
        imagesConf: Images = configuration.images
    ): List<ShowWithImages> {
        return storeImagesHelper.getImages(items, forceUpdate, imagesConf)
    }

    suspend fun getImages(
        item: eu.oncreate.bingie.data.local.model.trakt.SearchResultItem,
        forceUpdate: Boolean = false,
        imagesConf: Images = configuration.images
    ): ShowWithImages {
        return storeImagesHelper.getImages(item, forceUpdate, imagesConf)
    }

    suspend fun getSeasons(traktId: Int, forceUpdate: Boolean = false): List<LocalSeasonsItem> {
        return if (forceUpdate) {
            stores.seasons.fresh(traktId)
        } else {
            stores.seasons.get(traktId)
        }
    }

    suspend fun getShow(traktId: Int, forceUpdate: Boolean = false): LocalSearchResult {
        return if (forceUpdate) {
            stores.showStore.fresh(traktId)
        } else {
            stores.showStore.get(traktId)
        }.first()
    }
}
