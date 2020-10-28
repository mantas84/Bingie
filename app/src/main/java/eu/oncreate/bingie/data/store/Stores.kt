package eu.oncreate.bingie.data.store

import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.RoomDb
import eu.oncreate.bingie.data.store.stores.Configuration
import eu.oncreate.bingie.data.store.stores.Fanart
import eu.oncreate.bingie.data.store.stores.PopularShows
import eu.oncreate.bingie.data.store.stores.Search
import eu.oncreate.bingie.data.store.stores.Seasons
import eu.oncreate.bingie.data.store.stores.Shows
import eu.oncreate.bingie.data.store.stores.TmdbImages
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlin.time.ExperimentalTime

@OptIn(
    FlowPreview::class,
    ExperimentalCoroutinesApi::class,
    ExperimentalTime::class,
    ExperimentalStdlibApi::class
)
class Stores(
    roomDb: RoomDb,
    fanartApi: FanartApi,
    tmdbApi: TmdbApi,
    traktApi: TraktApi
) {

    val configuration = Configuration(roomDb, tmdbApi).configurationStore
    val fanart = Fanart(roomDb, fanartApi).fanartStore
    val seasons = Seasons(roomDb, traktApi).seasonsStore
    val tmdbImages = TmdbImages(roomDb, tmdbApi).tmdbImagesStore
    val search = Search(roomDb, traktApi).searchStore
    val popularShows = PopularShows(roomDb, traktApi).popularShowsStore
    val showStore = Shows(roomDb, traktApi).showStore
}
