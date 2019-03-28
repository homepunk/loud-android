package homepunk.github.com.presentation.feature.discover.event.model

import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingParentModel
import homepunk.github.com.presentation.util.DateTimeUtil

/**Created by Homepunk on 27.03.2019. **/
class UpcomingEventBindingParentModel(private var locationName: String? = "",
                                      var eventList: List<EventModel>) : ExpandableBindingParentModel<UpcomingEventBindingChildModel>() {

    override fun getLayoutId() = R.layout.layout_item_upcoming_event_parent

    override fun getBindingVariableId() = BR.parentModel

    init {
        if (locationName.equals("kharkiv", true)) {
            isParentExpanded.set(true)
        }

        var currentMonth = -1
        for (i in 0 until eventList.size) {
            val child = eventList[i]
            val monthNum = child.month.get()
            if (monthNum != -1 && monthNum != currentMonth) {
                currentMonth = monthNum

                children.add(UpcomingEventBindingChildModel(R.layout.layout_item_upcoming_event_timeline_month_child)
                        .apply { setUpMonth(i == 0, DateTimeUtil.getMonthForInt(child.month.get())) })
            }

            children.add(UpcomingEventBindingChildModel(R.layout.layout_item_upcoming_event_timeline_event_child)
                    .apply { setUpEvent(i == eventList.lastIndex, child) })
        }
    }

    @Bindable
    fun getLocationName() = locationName
}