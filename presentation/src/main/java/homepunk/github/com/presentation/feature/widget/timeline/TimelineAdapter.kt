package homepunk.github.com.presentation.feature.widget.timeline

import homepunk.github.com.presentation.core.listener.OnNotifyDataSeChangedListener
import java.util.*

/**Created by Homepunk on 14.05.2019. **/
abstract class TimelineAdapter<T> {
    var onNotifyDataSeChangedListener: OnNotifyDataSeChangedListener? = null
    var itemList: List<T>? = null
    set(value) {
        field = value
        onNotifyDataSeChangedListener?.onDataSetChanged()
    }

    abstract fun getDateLayoutId(): Int

    abstract fun getDefaultLayoutId(): Int

    abstract fun getDate(value: T) : Date

    abstract fun getDateText(value: T): String

    abstract fun getMonthText(value: T): String
}