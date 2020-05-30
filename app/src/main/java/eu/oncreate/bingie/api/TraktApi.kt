package eu.oncreate.bingie.api

import eu.oncreate.bingie.api.model.SearchResultItem
import eu.oncreate.bingie.api.model.Seasons
import eu.oncreate.bingie.api.model.SeasonsItem
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TraktApi {

    @GET("/search/show?extended=full&fields=title")
    fun search(@Query("query") query: String): Single<List<SearchResultItem>>

    @GET("/shows/{showId}/seasons?extended=full")
    fun showSeasons(@Path("showId") showId: String): Single<List<SeasonsItem>>

    @GET("/shows/{showId}/seasons/{season}?extended=full")
    fun showSeason(@Path("showId") showId: String, @Path("season") season: Int): Single<Seasons>
}
