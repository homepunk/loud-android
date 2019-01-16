package homepunk.github.com.presentation.core.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import homepunk.github.com.presentation.core.dagger.ViewModelFactory
import homepunk.github.com.presentation.core.dagger.ViewModelKey
import homepunk.github.com.presentation.feature.main.discover.DiscoverViewModel
import homepunk.github.com.presentation.feature.main.event.upcoming.UpcomingEventListViewModel

/**Created by Homepunk on 27.12.2018. **/
@Module
interface ViewModelBindingModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel::class )
    fun bindHomeFragmentViewModel(viewModel: DiscoverViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingEventListViewModel::class )
    fun bindUpcomingEventsViewModel(viewModel: UpcomingEventListViewModel): ViewModel
}