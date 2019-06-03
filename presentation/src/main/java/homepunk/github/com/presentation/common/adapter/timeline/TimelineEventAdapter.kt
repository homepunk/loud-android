package homepunk.github.com.presentation.common.adapter.timeline

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.databinding.LayoutItemTimelineEvent2Binding
import homepunk.github.com.presentation.feature.event.model.EventModel
import homepunk.github.com.presentation.feature.widget.timeline.TimelineAdapter
import java.text.SimpleDateFormat
import java.util.*

/**Created by Homepunk on 15.05.2019. **/

class TimelineEventAdapter : TimelineAdapter<EventModel>() {
    var onItemClickListener: OnItemClickListener<EventModel>? = null

    override fun getDate(value: EventModel): String {
        return value.event.start!!.date!!
    }

    override fun getDateFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    override fun onInflateLayout(context: Context, root: ViewGroup): ViewDataBinding {
        return LayoutItemTimelineEvent2Binding.inflate(LayoutInflater.from(context), root, true)
    }

    override fun onBindLayout(binding: ViewDataBinding, index: Int) {
        with(binding as LayoutItemTimelineEvent2Binding) {
            val eventModel = itemList!![index]
            model = eventModel
            cvEvent.setOnClickListener {
                onItemClickListener?.onClick(index, eventModel)
            }
        }
    }
}