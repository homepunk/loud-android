package homepunk.github.com.presentation.feature.detail.event

import android.os.Bundle
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.discover.event.model.EventModel



var KEY_EVENT_MODEL = "KEY_EVENT_MODEL"

class EventFragment : BaseFragment<FragmentEventBinding>() {
    override var layoutId = R.layout.fragment_event

    private lateinit var mViewModel: EventViewModel

    override fun init() {
        mViewModel = getViewModel(EventViewModel::class.java)

        arguments?.run {
            val eventModel = getSerializable(KEY_EVENT_MODEL) as EventModel
            with(mDataBinding) {
                model = eventModel
            }
        }
    }

    companion object {
        fun newInstance(item: EventModel): EventFragment {
            val fragment = EventFragment()
            fragment.arguments = Bundle()
                    .apply {
                        putSerializable(KEY_EVENT_MODEL, item)
                    }
            return fragment
        }
    }
}