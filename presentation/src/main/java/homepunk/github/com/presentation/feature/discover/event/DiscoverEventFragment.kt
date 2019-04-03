package homepunk.github.com.presentation.feature.discover.event

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.ExpandableBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.LocationEventBindingParentModel
import homepunk.github.com.presentation.feature.discover.event.model.UpcomingEventBindingChildModel

class DiscoverEventFragment : BaseFragment<FragmentEventBinding>() {
    private lateinit var eventListViewModel: DiscoverEventViewModel

    override var layoutId = R.layout.fragment_event

    override fun init() {
        eventListViewModel = getViewModel(DiscoverEventViewModel::class.java)

        with(mDataBinding) {
            rvPrimaryEvents.adapter = SimpleBindingRecyclerAdapter<EventModel>(R.layout.layout_item_primary_event, BR.model)
            rvUpcomingEvents.adapter = ExpandableBindingRecyclerAdapter<UpcomingEventBindingChildModel, LocationEventBindingParentModel>()

            viewModel = eventListViewModel
        }

        eventListViewModel.userLocationLiveData.observe(this, Observer {
            wLog("LIVA DATA CHANGED: ${it.locations.size}")
            eventListViewModel.fetchUpcomingEventList(it)
        })
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        eventListViewModel.fetchUpcomingEventList()
    }

    override fun onResume() {
        super.onResume()
        wLog("onResume")
    }
}