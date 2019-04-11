package homepunk.github.com.presentation.feature.discover.event

import androidx.lifecycle.Observer
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.ExpandableBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentDiscoverEventBinding
import homepunk.github.com.presentation.feature.detail.event.EventFragment
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.LocationEventBindingParentModel
import homepunk.github.com.presentation.feature.discover.event.model.UpcomingEventBindingChildModel
import homepunk.github.com.presentation.feature.widget.animation.AnimationEventLiveData

class DiscoverEventFragment : BaseFragment<FragmentDiscoverEventBinding>() {
    private lateinit var mViewModel: DiscoverEventViewModel

    override var layoutId = R.layout.fragment_discover_event

    override fun init() {
        mViewModel = getViewModel(DiscoverEventViewModel::class.java).apply {
            userLocationLiveData.observe(this@DiscoverEventFragment, Observer {
                updateUpcomingEventList(it)
            })

            onChildClickEventLiveData.observe(this@DiscoverEventFragment, Observer {
                val fragment = EventFragment.newInstance(it.event.get()!!)
                activity?.supportFragmentManager?.run {
                    beginTransaction().replace(android.R.id.content, fragment)
                            .addToBackStack(null)
                            .commit()
                }
            })
        }

        with(mDataBinding) {
            rvPrimaryEvents.adapter = SimpleBindingRecyclerAdapter<EventModel>(R.layout.layout_item_primary_event, BR.model)
            rvUpcomingEvents.adapter = ExpandableBindingRecyclerAdapter<UpcomingEventBindingChildModel, LocationEventBindingParentModel>()

            svRootLayout.viewTreeObserver.addOnScrollChangedListener {
                AnimationEventLiveData.getInstance().onScrollEvent(svRootLayout.scrollY)
            }
            viewModel = mViewModel
        }
    }

    override fun onResume() {
        super.onResume()
        wLog("onResume")
    }
}