package homepunk.github.com.presentation.feature.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.HORIZONTAL
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import homepunk.github.com.domain.model.songkick.SongkickArtist
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.R.layout
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentEventBinding
import homepunk.github.com.presentation.feature.event.model.EventModel
import homepunk.github.com.presentation.feature.youtube.YoutubeViewModel
import homepunk.github.com.presentation.feature.youtube.model.YoutubeVideoModel
import homepunk.github.com.presentation.util.layout.CustomLinearLayoutManager


class EventFragment : BaseFragment<FragmentEventBinding>() {
    override var layoutId = layout.fragment_event

    private lateinit var mViewModel: EventViewModel
    private lateinit var mYoutubeViewModel: YoutubeViewModel

    override fun init() {
        val eventList = arguments?.getSerializable(KEY_EVENT_MODEL_LIST) as ArrayList<EventModel>

        mYoutubeViewModel = getViewModel(YoutubeViewModel::class.java)
        mViewModel = getViewModel(EventViewModel::class.java)
        mViewModel.init(eventList)

        val fragment = YouTubePlayerSupportFragment.newInstance()
        val transaction = childFragmentManager.beginTransaction()
        fragment.initialize("AIzaSyBHmQY7e766b7Kcgtzw1dohgFjeadDFwU8", mYoutubeViewModel.onInitializedListener)
        transaction.add(R.id.youtube_fragment, fragment as Fragment).commit()

        mViewModel.onArtistClickLiveData.observe(this, Observer { mYoutubeViewModel.setUpQuery(it!!.displayName!!) })

        with(mDataBinding) {
            viewModel = mViewModel
            youtubeViewModel = mYoutubeViewModel

            rvLineUp.adapter = SimpleBindingRecyclerAdapter<SongkickArtist>(layout.layout_item_event_info_line_up, BR.model)
            rvYoutubePreview.adapter = SimpleBindingRecyclerAdapter<YoutubeVideoModel>(layout.layout_item_youtube_video_preview, BR.model)
            rvYoutubePreview.layoutManager = CustomLinearLayoutManager(context!!, HORIZONTAL, false)
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
        const val KEY_EVENT_MODEL_LIST = "KEY_EVENT_MODEL_LIST"

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