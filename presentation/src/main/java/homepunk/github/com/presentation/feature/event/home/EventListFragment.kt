package homepunk.github.com.presentation.feature.event.home

import androidx.lifecycle.Observer
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventListBinding
import homepunk.github.com.presentation.feature.event.EventFragment
import homepunk.github.com.presentation.feature.event.model.EventModel

class EventListFragment : BaseFragment<FragmentEventListBinding>() {
    private lateinit var mViewModel: EventListViewModel

    override var layoutId = R.layout.fragment_event_list

    override fun init() {
        mViewModel = getViewModel(EventListViewModel::class.java).apply {
            userLocationLiveData.observe(this@EventListFragment, Observer {
                updateUpcomingEventList(it)
            })

            onEventClickLiveData.observe(this@EventListFragment, Observer {
                val fragment = EventFragment.newInstance(it)
                activity?.supportFragmentManager?.run {
                    beginTransaction().replace(android.R.id.content, fragment)
                            .addToBackStack(null)
                            .commit()
                }
            })
        }

        with(mDataBinding) {
            rvFavoriteEvents.adapter = SimpleBindingRecyclerAdapter<EventModel>(R.layout.layout_item_popular_event, BR.model)
            rvNearestEvents.adapter = SimpleBindingRecyclerAdapter<EventModel>(R.layout.layout_item_primary_event, BR.model)

            svRootLayout.viewTreeObserver.addOnScrollChangedListener {
//                AnimationEventLiveData.getInstance().onScrollEvent(ScrollEvent(svRootLayout.scrollY, svRootLayout.getChildAt(0).height))
            }
            viewModel = mViewModel
        }
    }

    override fun onResume() {
        super.onResume()
        wLog("onResume")
    }
}