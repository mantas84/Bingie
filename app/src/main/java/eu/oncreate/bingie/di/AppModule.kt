package eu.oncreate.bingie.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import eu.oncreate.bingie.data.Datasource
import eu.oncreate.bingie.data.api.FanartApi
import eu.oncreate.bingie.data.api.TmdbApi
import eu.oncreate.bingie.data.api.TraktApi
import eu.oncreate.bingie.data.local.RoomDb
import javax.inject.Singleton

// @Module(includes = [ViewModelModule::class])
// @Module(includes = [NetModule::class])
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideRoomDb(context: Context) = RoomDb.getInstance(context)

    @Provides
    @Singleton
    fun provideDataSource(
        roomDb: RoomDb,
        traktApi: TraktApi,
        fanartApi: FanartApi,
        tmdbApi: TmdbApi
    ) = Datasource(roomDb, fanartApi, tmdbApi, traktApi)
}
