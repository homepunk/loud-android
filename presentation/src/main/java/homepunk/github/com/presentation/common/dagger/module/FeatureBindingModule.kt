package homepunk.github.com.presentation.common.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import homepunk.github.com.presentation.feature.discover.DiscoverHostFragment
import homepunk.github.com.presentation.feature.discover.event.EventDiscoverFragment
import homepunk.github.com.presentation.feature.discover.library.LibraryDiscoverFragment
import homepunk.github.com.presentation.feature.main.MainActivity

/**Created by Homepunk on 27.12.2018. **/
@Module()
interface FeatureBindingModule {
    @ContributesAndroidInjector
    fun mainAcitivity(): MainActivity

    @ContributesAndroidInjector
    fun discoverHostFragment(): DiscoverHostFragment

    @ContributesAndroidInjector
    fun discoverlibraryFragment(): LibraryDiscoverFragment

    @ContributesAndroidInjector
    fun discoverEventFragment(): EventDiscoverFragment
}
