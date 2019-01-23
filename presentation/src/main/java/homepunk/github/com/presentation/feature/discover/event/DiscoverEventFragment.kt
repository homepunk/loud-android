package homepunk.github.com.presentation.feature.discover.event

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.discover.event.upcoming.EventListViewModel

class DiscoverEventFragment : BaseFragment<FragmentEventBinding>() {
    private lateinit var eventListViewModel: EventListViewModel

    override fun getLayoutResId() = R.layout.fragment_event

    override fun init() {
        eventListViewModel = getViewModel(EventListViewModel::class.java)
        mDataBinding.viewModel = eventListViewModel
    }

    override fun onResume() {
        super.onResume()
        eventListViewModel.fetchUpcomingEventList()
    }
}