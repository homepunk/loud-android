package homepunk.github.com.presentation.feature.widget.timeline.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

/**Created by Homepunk on 15.05.2019. **/
data class TimelineEventModel<T>(var item: T,
                                 var dayIndex: Int,
                                 var monthIndex: Int,
                                 var itemType: Int,
                                 var getHeaderDateText: (T) -> String) : BaseObservable() {
    companion object {
        val ITEM = 0
        val HEADER = 1
    }

    @Bindable
    fun getDay(): String {
        return getHeaderDateText(item)
    }
}