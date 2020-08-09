package eu.oncreate.bingie.data.api

import eu.oncreate.bingie.BuildConfig
import eu.oncreate.bingie.data.api.model.tmdb.Configuration
import eu.oncreate.bingie.data.api.model.tmdb.TmdbImages
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("tv/{tv_id}/images")
    fun getImages(@Path("tv_id") tvId: Int, @Query("api_key") apiKey: String = BuildConfig.TMDB_TOKEN): Single<TmdbImages>

    @GET("configuration")
    fun getConfiguration(@Query("api_key") apiKey: String = BuildConfig.TMDB_TOKEN): Single<Configuration>
}
