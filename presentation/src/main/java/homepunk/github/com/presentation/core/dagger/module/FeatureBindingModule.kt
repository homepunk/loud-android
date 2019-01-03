package homepunk.github.com.presentation.core.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import homepunk.github.com.presentation.feature.main.MainActivity
import homepunk.github.com.presentation.feature.main.home.HomeFragment

/**Created by Homepunk on 27.12.2018. **/
@Module()
interface FeatureBindingModule {
    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun mainAcitivity(): MainActivity
}