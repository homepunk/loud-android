package homepunk.github.com.presentation.feature.main.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun createViewModel() = ViewModelProviders.of(this, viewModelFactory)[HomeFragmentViewModel::class.java]

    override fun getLayoutResId() = R.layout.fragment_home

    override fun init() {
        mViewModel.getLatestReleases()
    }
}