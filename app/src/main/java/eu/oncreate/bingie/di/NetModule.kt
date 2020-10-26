package eu.oncreate.bingie.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import eu.oncreate.bingie.BuildConfig
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@Suppress("MagicNumber")
class NetModule {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10L * 1024 * 1024
        return Cache(application.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        val moshiBuilder = Moshi.Builder()
        moshiBuilder.add(Date::class.java, Rfc3339DateJsonAdapter()).add(KotlinJsonAdapterFactory())
        return moshiBuilder.build()
    }

    @Provides
    @Singleton
    @Named(Trakt)
    fun provideOkhttpClient(cache: Cache): OkHttpClient {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            addInterceptor(SupportInterceptor())
            cache(cache)
        }.build()
    }

    @Provides
    @Singleton
    @Named(Tmdb)
    fun provideOkhttpClientTmdb(cache: Cache): OkHttpClient {

        val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder().apply {
            addInterceptor(interceptor)
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            cache(cache)
        }.build()
    }

    class SupportInterceptor : Interceptor {

        /**
         * Interceptor class for setting of the headers for every request
         */
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("trakt-api-key", BuildConfig.TRAKT_CLIENT_ID)
                .addHeader("trakt-api-version", "2")
                .build()
            return chain.proceed(request)
        }

//        /**
//         * Authenticator for when the authToken need to be refresh and updated
//         * everytime we get a 401 error code
//         */
//        @Throws(IOException::class)
//        override fun authenticate (route: Route?, response: Response?): Request? {
//            var requestAvailable: Request? = null
//            try {
//                requestAvailable = response?.request?.newBuilder()
//                    ?.addHeader("AUTH_TOKEN", "UUID.randomUUID().toString()")
//                    ?.build()
//                return requestAvailable
//            } catch (ex: Exception) { }
//            return requestAvailable
//        }
    }

    @Provides
    @Singleton
    @Named(Trakt)
    fun provideRetrofitTrakt(moshi: Moshi, @Named(Trakt) okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = if (BuildConfig.FLAVOR == "stagging") {
            "https://api-staging.trakt.tv"
        } else {
            "https://api.trakt.tv"
        }
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named(Tmdb)
    fun provideRetrofitTmdb(moshi: Moshi, @Named(Tmdb) okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://api.themoviedb.org/3/"
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @Named(Fanart)
    fun provideRetrofitFanart(moshi: Moshi, @Named(Tmdb) okHttpClient: OkHttpClient): Retrofit {
        val baseUrl = "https://webservice.fanart.tv/v3/"
        return Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideTraktApi(@Named(Trakt) retrofit: Retrofit): TraktApi {
        return retrofit.create(TraktApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTmdbApi(@Named(Tmdb) retrofit: Retrofit): TmdbApi {
        return retrofit.create(TmdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFanartApi(@Named(Fanart) retrofit: Retrofit): FanartApi {
        return retrofit.create(FanartApi::class.java)
    }

    companion object {
        const val Trakt = "Trakt"
        const val Tmdb = "Tmdb"
        const val Fanart = "Fanart"
    }
}
