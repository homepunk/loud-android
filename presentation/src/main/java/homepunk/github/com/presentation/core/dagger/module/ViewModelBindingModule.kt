package homepunk.github.com.presentation.core.dagger.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import homepunk.github.com.presentation.core.dagger.ViewModelFactory
import homepunk.github.com.presentation.core.dagger.ViewModelKey
import homepunk.github.com.presentation.feature.main.home.HomeFragmentViewModel

/**Created by Homepunk on 27.12.2018. **/
@Module
interface ViewModelBindingModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel::class )
    fun bindHomeFragmentViewModel(viewModel: HomeFragmentViewModel): ViewModel
}