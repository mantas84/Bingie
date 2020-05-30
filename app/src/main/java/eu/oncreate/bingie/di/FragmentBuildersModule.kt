package eu.oncreate.bingie.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import eu.oncreate.bingie.fragment.list.ListFragment

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment
}
