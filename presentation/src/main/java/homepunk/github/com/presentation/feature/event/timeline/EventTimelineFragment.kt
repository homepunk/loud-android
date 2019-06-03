package homepunk.github.com.presentation.feature.event.timeline

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.databinding.EventTimelineFragmentBinding
import homepunk.github.com.presentation.feature.event.EventFragment
import homepunk.github.com.presentation.feature.event.home.EventListViewModel
import homepunk.github.com.presentation.feature.event.home.model.LocationEventTimelineModel

class EventTimelineFragment() : BaseFragment<EventTimelineFragmentBinding>() {
    private lateinit var mViewModel: EventTimelineViewModel

    override var layoutId = R.layout.event_timeline_fragment

    override fun init() {
        mViewModel = getViewModel(EventTimelineViewModel::class.java).apply {
            userLocationLiveData.observe(this@EventTimelineFragment, Observer {
                updateUpcomingEventList(it)
            })
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
            rvUpcomingEvents.adapter = SimpleBindingRecyclerAdapter<LocationEventTimelineModel>(R.layout.layout_item_location_event_timeline, BR.model)

            viewModel = mViewModel
        }
    }

    companion object {
        fun newInstance() = EventTimelineFragment()
    }
}
