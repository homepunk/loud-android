package homepunk.github.com.presentation.feature.event.timeline

import androidx.lifecycle.Observer
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.timeline.TimelineEventAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventTimelineBinding
import homepunk.github.com.presentation.feature.event.EventFragment


class EventTimelineFragment : BaseFragment<FragmentEventTimelineBinding>() {
    private lateinit var mViewModel: EventTimelineViewModel

    override var layoutId = R.layout.fragment_event_timeline

    override fun init() {
        mViewModel = getViewModel(EventTimelineViewModel::class.java).apply {
             onEventClickLiveData.observe(this@EventTimelineFragment, Observer {
                val fragment = EventFragment.newInstance(it)
                activity?.supportFragmentManager?.run {
                    beginTransaction().replace(android.R.id.content, fragment)
                            .addToBackStack(null)
                            .commit()
                }
            })

        }
        with(mDataBinding) {
//            rvUpcomingEvents.adapter = SimpleBindingRecyclerAdapter<LocationEventTimelineModel>(R.layout.layout_item_location_event_timeline, BR.model)
            viewModel = mViewModel
            timeline.adapter = TimelineEventAdapter()
        }
    }

    companion object {
        fun newInstance() = EventTimelineFragment()
    }
}
