package homepunk.github.com.presentation.feature.discover

import androidx.lifecycle.Observer
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.DiscoverHostFragmentBinding

class DiscoverHostFragment : BaseFragment<DiscoverHostFragmentBinding>() {
    private lateinit var viewModel: DiscoverHostViewModel

    override fun getLayoutResId() = R.layout.discover_host_fragment

    override fun initViewModels() {
        viewModel = getViewModel(DiscoverHostViewModel::class.java)
    }

    override fun init() {
        viewModel.init()
        viewModel.fragmentLiveData.observe(this, Observer {
            childFragmentManager.beginTransaction().replace(R.id.container, it).commit()
        })
    }
}
