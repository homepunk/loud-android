package homepunk.github.com.vinyl_underground.feature.main.home

import homepunk.github.com.vinyl_underground.R
import homepunk.github.com.vinyl_underground.base.BaseFragment
import homepunk.github.com.vinyl_underground.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>(){
    override fun createViewModel() = HomeFragmentViewModel(context!!)

    override fun getLayoutResId() = R.layout.fragment_home

    override fun onRestart() {

    }
    override fun init() {

    }
}