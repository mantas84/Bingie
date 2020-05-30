package eu.oncreate.bingie.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.oncreate.bingie.MainActivity

@Suppress("unused")
@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivityModule(): MainActivity
}
