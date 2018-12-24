package homepunk.github.com.presentation.feature.main.home

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.VUApplication
import homepunk.github.com.presentation.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>(){
    override fun createViewModel() = HomeFragmentViewModel(context!!)

    override fun getLayoutResId() = R.layout.fragment_home

    override fun onRestart() {

    }
    override fun init() {
        (activity?.application as VUApplication).run {
            appComponent.inject(mViewModel)
        }
        mViewModel.getReleaseList()
    }
}