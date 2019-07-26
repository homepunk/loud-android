package homepunk.github.com.presentation.feature.widget.timeline.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 23.07.2019. **/
class TimelineMonthModel(var name: String, isCurrent: Boolean = false) : BaseObservable() {
    var isCurrent = ObservableBoolean(isCurrent)
}