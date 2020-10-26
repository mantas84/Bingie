package eu.oncreate.bingie.data.api

import eu.oncreate.bingie.data.api.model.trakt.SearchResultItem
import eu.oncreate.bingie.data.api.model.trakt.Seasons
import eu.oncreate.bingie.data.api.model.trakt.SeasonsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TraktApi {

    @GET("/search/show?extended=full&fields=title")
    suspend fun search(
        @Query("query") query: String,
        @Query("limit") limit: String = "25",
        @Query("page") page: Int = 1,
    ): Response<List<SearchResultItem>>

    @GET("/search/trakt/{id}?type=show&extended=full&fields=title")
    suspend fun searchLookUp(@Path("id") traktId: Int): List<SearchResultItem>

    @GET("/shows/{showId}/seasons?extended=full")
    suspend fun showSeasons(@Path("showId") showId: Int): List<SeasonsItem>

    @GET("/shows/{showId}/seasons/{season}?extended=full")
    suspend fun showSeason(@Path("showId") showId: Int, @Path("season") season: Int): Seasons
}
