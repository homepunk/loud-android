package homepunk.github.com.presentation.core.ext

import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.common.adapter.timeline.TimelineEventAdapter
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.event.model.EventModel
import homepunk.github.com.presentation.feature.widget.timeline.TimelineAdapter
import homepunk.github.com.presentation.feature.widget.timeline.TimelineView

/**Created by Homepunk on 15.05.2019. **/

@BindingAdapter("timelineAdapter")
fun <T> TimelineView<T>.bindAdapter(adapter: TimelineAdapter<T>) {
    this.adapter = adapter
}

@BindingAdapter("timelineClickListener")
fun <T> TimelineView<T>.timelineClickListener(listener: OnItemClickListener<T>) {
    (adapter as? TimelineEventAdapter)?.let { it.onItemClickListener = listener as OnItemClickListener<EventModel> }

}

@BindingAdapter("timelineList")
fun <T> TimelineView<T>.timelineList(itemList: List<T>?) {
    if (itemList != null) {
        (adapter as? TimelineEventAdapter)?.let { it.itemList = itemList as List<EventModel> }
    }
}

