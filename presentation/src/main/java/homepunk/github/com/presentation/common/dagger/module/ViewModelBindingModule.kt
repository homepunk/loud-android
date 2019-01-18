package homepunk.github.com.presentation.common.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import homepunk.github.com.presentation.common.dagger.ViewModelFactory
import homepunk.github.com.presentation.common.dagger.ViewModelKey
import homepunk.github.com.presentation.feature.main.MainActivityViewModel
import homepunk.github.com.presentation.feature.main.discover.DiscoverViewModel
import homepunk.github.com.presentation.feature.mode.event.upcoming.UpcomingEventListViewModel

/**Created by Homepunk on 27.12.2018. **/
@Module
interface ViewModelBindingModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class )
    fun bindMainActivityViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class )
    fun bindHomeFragmentViewModel(viewModel: DiscoverViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingEventListViewModel::class )
    fun bindUpcomingEventsViewModel(viewModel: UpcomingEventListViewModel): ViewModel
}