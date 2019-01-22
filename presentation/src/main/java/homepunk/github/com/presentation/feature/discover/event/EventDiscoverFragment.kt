package homepunk.github.com.presentation.feature.discover.event

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.discover.event.upcoming.UpcomingEventListViewModel

class EventDiscoverFragment : BaseFragment<FragmentEventBinding>() {
    private lateinit var upcomingEventListViewModel: UpcomingEventListViewModel

    override fun getLayoutResId() = R.layout.fragment_event

    override fun init() {
        upcomingEventListViewModel = getViewModel(UpcomingEventListViewModel::class.java)
        mDataBinding.run {
            viewModel = upcomingEventListViewModel
        }
    }

    override fun onResume() {
        super.onResume()
        upcomingEventListViewModel.fetchUpcomingEventList()
    }
}