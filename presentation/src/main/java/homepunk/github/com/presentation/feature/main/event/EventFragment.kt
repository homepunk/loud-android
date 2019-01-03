package homepunk.github.com.presentation.feature.main.event

import androidx.recyclerview.widget.LinearLayoutManager
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingFragment
import homepunk.github.com.presentation.databinding.FragmentDiscoverBinding
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.adapter.recycler.UpcomingEventRvAdapter
import homepunk.github.com.presentation.feature.main.discover.DiscoverLatestViewModel
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration

class EventFragment : BaseBindingFragment<FragmentEventBinding>() {
    private lateinit var upcomingEventsViewModel: UpcomingEventsViewModel

    lateinit var upcomingEventRvAdapter: UpcomingEventRvAdapter

    override fun getLayoutResId() = R.layout.fragment_event

    override fun initViewModels() {
        upcomingEventsViewModel = getViewModel(UpcomingEventsViewModel::class.java)
        upcomingEventsViewModel.bind(this)
    }

    override fun init() {
        mDataBinding.run {
            sectionDivider.sectionTitle = getString(R.string.title_upcoming_event)
            upcomingEventRvAdapter = UpcomingEventRvAdapter()
            rvUpcomingEvent.adapter = upcomingEventRvAdapter
            rvUpcomingEvent.addItemDecoration(MarginItemDecoration(
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    0,
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    getDimen(R.dimen.margin_default_10dp).toInt()))
        }
        upcomingEventsViewModel.getUpcomingEventList()
    }
}