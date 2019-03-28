package homepunk.github.com.presentation.feature.discover.event.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.library.baseAdapters.BR
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingChildModel

/**Created by Homepunk on 27.03.2019. **/
class UpcomingEventBindingChildModel(private var layoutId: Int) : ExpandableBindingChildModel() {
    // Required MONTH fields
    var isUpcoming = ObservableBoolean(false)
    var month = ObservableField<String>()

    // Required EVENT fields
    var isLast = ObservableBoolean(false)
    var event = ObservableField<EventModel>()

    override fun getLayoutId() = layoutId

    override fun getBindingVariableId() = BR.model

    fun setUpMonth(isUpcoming: Boolean, month: String) {
        this.isUpcoming.set(isUpcoming)
        this.month.set(month)
    }

    fun setUpEvent(isLast: Boolean, event: EventModel) {
        this.isLast.set(isLast)
        this.event.set(event)
    }
}