package homepunk.github.com.presentation.common.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import homepunk.github.com.presentation.feature.discover.DiscoverHostFragment
import homepunk.github.com.presentation.feature.discover.event.DiscoverEventFragment
import homepunk.github.com.presentation.feature.discover.library.DiscoverLibraryFragment
import homepunk.github.com.presentation.feature.main.MainActivity
import homepunk.github.com.presentation.feature.menu.CountryListFragment
import homepunk.github.com.presentation.feature.menu.LanguageListFragment
import homepunk.github.com.presentation.feature.menu.MenuActivity

/**Created by Homepunk on 27.12.2018. **/
@Module()
interface FeatureBindingModule {
    @ContributesAndroidInjector
    fun mainAcitivity(): MainActivity

    @ContributesAndroidInjector
    fun menuAcitivity(): MenuActivity

    @ContributesAndroidInjector
    fun discoverHostFragment(): DiscoverHostFragment

    @ContributesAndroidInjector
    fun discoverlibraryFragment(): DiscoverLibraryFragment

    @ContributesAndroidInjector
    fun discoverEventFragment(): DiscoverEventFragment

    @ContributesAndroidInjector
    fun countryListFragment(): CountryListFragment

    @ContributesAndroidInjector
    fun languageListFragment(): LanguageListFragment
}
