package eu.oncreate.bingie.data.local

import eu.oncreate.bingie.data.api.model.fanart.FanartImages
import eu.oncreate.bingie.data.local.model.mapping.getLocal
import eu.oncreate.bingie.data.local.model.tmdb.Configuration
import eu.oncreate.bingie.data.local.model.tmdb.TmdbImages
import eu.oncreate.bingie.data.local.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.local.model.trakt.SeasonsItem
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LocalSource @Inject constructor(private val roomDb: RoomDb) {

    fun getConfiguration() = roomDb.configurationDao().getConfiguration()

    fun insertConfiguration(configuration: Configuration) =
        roomDb.configurationDao().insertConfiguration(configuration)

    fun searchItems(query: String): Single<List<SearchResultItem>> {
        return roomDb.searchResultItemDao().searchSearchResultItem(query)
    }

    fun getFanart(tvdbId: Int) = roomDb.fanartImageDao().getFanart(tvdbId)

    fun insertFanart(images: FanartImages): Completable {
        return roomDb.fanartImageDao().insertFanart(
            eu.oncreate.bingie.data.local.model.fanart.FanartImages.toLocal(images)
        )
    }

    fun getTmdbImages(tmdbId: Int): Single<List<TmdbImages>> {
        return roomDb.tmdbImagesDao().getTmdbImages(tmdbId)
    }

    fun insertTmdbImages(images: eu.oncreate.bingie.data.api.model.tmdb.TmdbImages): Completable {
        return roomDb.tmdbImagesDao().insertTmdbImages(TmdbImages.toLocal(images))
    }

    fun getSeasons(traktId: Int): Single<List<SeasonsItem>> {
        return roomDb.seasonsItemDao().getSeasonsItem(traktId)
    }

    fun insertSeasons(seasons: List<eu.oncreate.bingie.data.api.model.SeasonsItem>, traktId: Int): Completable {
        return roomDb.seasonsItemDao()
            .insertAllSeasonsItems(seasons.map { getLocal(it, traktId) })
    }

    fun getShow(traktId: Int): Single<List<SearchResultItem>> {
        return roomDb
            .searchResultItemDao()
            .getSearchResultItem(traktId)
    }

    fun insertShow(searchResults: List<eu.oncreate.bingie.data.api.model.SearchResultItem>): Completable {
        return roomDb
            .searchResultItemDao()
            .insertAllSearchResultItem(searchResults.map { SearchResultItem.toLocal(it) })
    }
}
