package homepunk.github.com.presentation.feature.mode.event

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.mode.event.upcoming.UpcomingEventListViewModel

class EventFragment : BaseBindingFragment<FragmentEventBinding>() {
    private lateinit var upcomingEventListViewModel: UpcomingEventListViewModel

    override fun getLayoutResId() = R.layout.fragment_event

    override fun initViewModels() {
        upcomingEventListViewModel = getViewModel(UpcomingEventListViewModel::class.java)
    }

    override fun init() {
        mDataBinding.run {
            viewModel = upcomingEventListViewModel
        }
    }

    override fun onResume() {
        super.onResume()
        upcomingEventListViewModel.fetchUpcomingEventList()
    }
}