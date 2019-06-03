package homepunk.github.com.presentation.feature.event.home.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import homepunk.github.com.presentation.common.adapter.timeline.TimelineEventAdapter
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.event.model.EventModel

/**Created by Homepunk on 10.05.2019. **/
class LocationEventTimelineModel(locationName: String? = "",
                                 var eventList: List<EventModel>,
                                 onItemClickListener: OnItemClickListener<EventModel>) : BaseObservable() {

    val locationName = ObservableField<String>(locationName)
    var timelineEventAdapter = TimelineEventAdapter()

    init {
        timelineEventAdapter.onItemClickListener = onItemClickListener
        timelineEventAdapter.itemList = eventList
                .filter {
                    it.event.start != null && it.event.start!!.date != null
                }
                .toArrayList()
    }
}