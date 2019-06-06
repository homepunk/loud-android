package homepunk.github.com.presentation.core.ext

import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.feature.widget.expandablelayout.ExpandableHeader
import homepunk.github.com.presentation.feature.widget.timeline.TimelineAdapter
import homepunk.github.com.presentation.feature.widget.timeline.TimelineView

/**Created by Homepunk on 15.05.2019. **/

@BindingAdapter("timeline_adapter")
fun <T> TimelineView<T>.bindAdapter(adapter: TimelineAdapter<T>) {
    this.adapter = adapter
}

