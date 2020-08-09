package eu.oncreate.bingie.data.api

import eu.oncreate.bingie.data.api.model.SearchResultItem
import eu.oncreate.bingie.data.api.model.Seasons
import eu.oncreate.bingie.data.api.model.SeasonsItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TraktApi {

    @GET("/search/show?extended=full&fields=title")
    fun search(
        @Query("query") query: String,
        @Query("limit") limit: String = "25"
    ): Single<List<SearchResultItem>>

    @GET("/search/trakt/{id}?type=show&extended=full&fields=title")
    fun searchLookUp(@Path("id") traktId: Int): Single<List<SearchResultItem>>

    @GET("/shows/{showId}/seasons?extended=full")
    fun showSeasons(@Path("showId") showId: Int): Single<List<SeasonsItem>>

    @GET("/shows/{showId}/seasons/{season}?extended=full")
    fun showSeason(@Path("showId") showId: Int, @Path("season") season: Int): Single<Seasons>
}
