package homepunk.github.com.presentation.common.adapter.timeline

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.ext.dayOfMonth
import homepunk.github.com.presentation.core.ext.dayOfWeek
import homepunk.github.com.presentation.core.ext.monthName
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.event.model.EventModel
import homepunk.github.com.presentation.feature.widget.timeline.TimelineAdapter
import java.text.SimpleDateFormat
import java.util.*

/**Created by Homepunk on 15.05.2019. **/

class TimelineEventAdapter : TimelineAdapter<EventModel>() {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    var onItemClickListener: OnItemClickListener<EventModel>? = null

    override fun getDateLayoutId() = R.layout.custom_layout_item_timeline_day

    override fun getDefaultLayoutId() = R.layout.custom_layout_item_timeline_event

    override fun getDate(value: EventModel)= dateFormat.parse(value.event.start!!.date!!)!!

    override fun getDateText(value: EventModel) = getDate(value).run { "${dayOfWeek()}, ${dayOfMonth()}" }

    override fun getMonthText(value: EventModel) = getDate(value).run { monthName() }
}