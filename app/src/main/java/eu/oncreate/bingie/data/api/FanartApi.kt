package eu.oncreate.bingie.data.api

import eu.oncreate.bingie.BuildConfig
import eu.oncreate.bingie.data.api.model.fanart.FanartImages
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FanartApi {

    @GET("tv/{id}")
    fun getImages(
        @Path("id") tvdbId: Int,
        @Query("api_key") apiKey: String = BuildConfig.FANART_KEY
    ): Single<FanartImages>
}
