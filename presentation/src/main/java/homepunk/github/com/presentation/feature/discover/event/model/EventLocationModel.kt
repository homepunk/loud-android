package homepunk.github.com.presentation.feature.discover.event.model

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean
import homepunk.github.com.presentation.common.model.BooleanHolder
import homepunk.github.com.presentation.core.ext.swap

/**Created by Homepunk on 11.01.2019. **/
class EventLocationModel(private var locationName: String? = "",
                         private var eventList: List<EventModel> = arrayListOf()) : BaseObservable() {
    var isParentExpanded = ObservableBoolean(false)

    init {
        if (locationName.equals("kharkiv", true)) {
            isParentExpanded.set(true)
        }
    }

    @Bindable
    fun getEvents() = eventList

    @Bindable
    fun getLocationName() = locationName

    @Bindable
    fun getOnParentClickListener() = View.OnClickListener { isParentExpanded.swap() }
}
