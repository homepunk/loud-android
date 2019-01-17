package homepunk.github.com.presentation.common.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import homepunk.github.com.presentation.feature.main.MainActivity
import homepunk.github.com.presentation.feature.main.discover.DiscoverFragment
import homepunk.github.com.presentation.feature.main.event.EventFragment

/**Created by Homepunk on 27.12.2018. **/
@Module()
interface FeatureBindingModule {
    @ContributesAndroidInjector
    fun homeFragment(): DiscoverFragment

    @ContributesAndroidInjector
    fun eventFragment(): EventFragment

    @ContributesAndroidInjector
    fun mainAcitivity(): MainActivity
}