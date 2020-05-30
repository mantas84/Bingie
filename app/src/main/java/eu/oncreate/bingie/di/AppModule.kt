package eu.oncreate.bingie.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// @Module(includes = [ViewModelModule::class])
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideSharedPrefs(appContext: Context): SharedPreferences =
        appContext.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application
}
