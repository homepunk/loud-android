package homepunk.github.com.presentation.feature.releases

import androidx.lifecycle.Observer
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.DiscoverHostFragmentBinding

class DiscoverHostFragment : BaseFragment<DiscoverHostFragmentBinding>() {
    private lateinit var viewModel: DiscoverHostViewModel

    override var layoutId = R.layout.discover_host_fragment

    override fun init() {
        viewModel = getViewModel(DiscoverHostViewModel::class.java)
        viewModel.fragmentLiveData.observe(this, Observer {
            if (it != null) {
                childFragmentManager.beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .replace(R.id.container, it).commit()
            }
        })
    }
}
