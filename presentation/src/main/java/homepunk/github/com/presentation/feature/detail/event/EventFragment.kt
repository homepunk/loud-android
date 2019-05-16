package homepunk.github.com.presentation.feature.detail.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import homepunk.github.com.domain.model.songkick.SongkickArtist
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.discover.event.model.EventModel


var KEY_EVENT_MODEL_LIST = "KEY_EVENT_MODEL_LIST"

class EventFragment : BaseFragment<FragmentEventBinding>() {
    override var layoutId = R.layout.fragment_event

    private lateinit var mViewModel: EventViewModel

    override fun init() {
        val eventList = arguments?.getSerializable(KEY_EVENT_MODEL_LIST) as ArrayList<EventModel>

        mViewModel = getViewModel(EventViewModel::class.java)
        mViewModel.init(eventList)

        with(mDataBinding) {
            viewModel = mViewModel

            rvLineUp.adapter = SimpleBindingRecyclerAdapter<SongkickArtist>(R.layout.layout_item_event_info_line_up, BR.model)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        mDataBinding.venueMap.onCreate(savedInstanceState)
        return root
    }

    override fun onResume() {
        super.onResume()
        mDataBinding.venueMap.onResume()
    }

    override fun onStart() {
        super.onStart()
        mDataBinding.venueMap.onStart()
    }

    override fun onPause() {
        super.onPause()
        mDataBinding.venueMap.onPause()
    }

    override fun onDestroy() {
        mDataBinding.venueMap.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDataBinding.venueMap.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mDataBinding.venueMap.onLowMemory()
    }

    companion object {
        fun newInstance(itemList: ArrayList<EventModel>): EventFragment {
            val fragment = EventFragment()
            fragment.arguments = Bundle()
                    .apply {
                        putSerializable(KEY_EVENT_MODEL_LIST, itemList)
                    }
            return fragment
        }
    }
}