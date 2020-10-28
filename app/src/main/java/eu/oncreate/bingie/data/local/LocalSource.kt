package eu.oncreate.bingie.data.local

import eu.oncreate.bingie.data.api.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.tmdb.Configuration
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import javax.inject.Inject

class LocalSource @Inject constructor(private val roomDb: RoomDb) {

    suspend fun getConfiguration() = roomDb.configurationDao().getConfiguration()

    suspend fun insertConfiguration(configuration: Configuration) =
        roomDb.configurationDao().insertConfiguration(configuration)

    suspend fun searchItems(query: String): List<SearchResultItem> {
        return roomDb.searchResultItemDao().searchSearchResultItem(query)
    }

    suspend fun getFanart(tvdbId: Int) = roomDb.fanartImageDao().getFanart(tvdbId)

    suspend fun insertFanart(images: FanartImages) {
        return roomDb.fanartImageDao().insertFanart(
            eu.oncreate.bingie.data.local.model.fanart.FanartImages.toLocal(images)
        )
    }

    suspend fun getTmdbImages(tmdbId: Int): List<TmdbImages> {
        return roomDb.tmdbImagesDao().getTmdbImages(tmdbId)
    }

    suspend fun insertTmdbImages(images: eu.oncreate.bingie.data.api.model.tmdb.TmdbImages) {
        return roomDb.tmdbImagesDao().insertTmdbImages(TmdbImages.toLocal(images))
    }

    suspend fun getSeasons(traktId: Int): List<SeasonsItem> {
        return roomDb.seasonsItemDao().getSeasonsItem(traktId)
    }

    suspend fun insertSeasons(seasons: List<eu.oncreate.bingie.data.api.model.trakt.SeasonsItem>, traktId: Int) {
        return roomDb.seasonsItemDao()
            .insertAllSeasonsItems(seasons.map { getLocal(it, traktId) })
    }

    suspend fun getShow(traktId: Int): List<SearchResultItem> {
        return roomDb
            .searchResultItemDao()
            .getSearchResultItem(traktId)
    }

    suspend fun insertShow(searchResults: List<eu.oncreate.bingie.data.api.model.trakt.SearchResultItem>) {
        return roomDb
            .searchResultItemDao()
            .insertAllSearchResultItem(searchResults.map { SearchResultItem.toLocal(it) })
    }
}
