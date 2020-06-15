package eu.oncreate.bingie.api

import eu.oncreate.bingie.api.model.fanart.FanartImages
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FanartApi {

    @GET("tv/{id}")
    fun getImages(
        @Path("id") tvdbId: Int,
        @Query("api_key") apiKey: String
    ): Single<FanartImages>
}
