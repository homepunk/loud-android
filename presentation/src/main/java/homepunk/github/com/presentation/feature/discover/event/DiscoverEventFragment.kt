package homepunk.github.com.presentation.feature.discover.event

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding

class DiscoverEventFragment : BaseFragment<FragmentEventBinding>() {
    private lateinit var eventListViewModel: DiscoverEventViewModel

    override fun getLayoutResId() = R.layout.fragment_event

    override fun init() {
        eventListViewModel = getViewModel(DiscoverEventViewModel::class.java)
        mDataBinding.viewModel = eventListViewModel
    }

    override fun onResume() {
        super.onResume()
        eventListViewModel.fetchUpcomingEventList()
    }
}