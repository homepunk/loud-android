package homepunk.github.com.presentation.core.dagger.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import homepunk.github.com.presentation.feature.detail.event.EventFragment
import homepunk.github.com.presentation.feature.discover.DiscoverHostFragment
import homepunk.github.com.presentation.feature.discover.event.EventListFragment
import homepunk.github.com.presentation.feature.discover.library.DiscoverLibraryFragment
import homepunk.github.com.presentation.feature.main.MainActivity
import homepunk.github.com.presentation.feature.menu.MenuActivity
import homepunk.github.com.presentation.feature.menu.country.CountryListFragment
import homepunk.github.com.presentation.feature.menu.language.LanguageListFragment

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
    fun discoverEventFragment(): EventListFragment

    @ContributesAndroidInjector
    fun countryListFragment(): CountryListFragment

    @ContributesAndroidInjector
    fun languageListFragment(): LanguageListFragment

    @ContributesAndroidInjector
    fun eventFragment(): EventFragment
}
