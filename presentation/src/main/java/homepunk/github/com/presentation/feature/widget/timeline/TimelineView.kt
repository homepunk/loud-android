package homepunk.github.com.presentation.feature.widget.timeline

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import homepunk.github.com.presentation.core.ext.dayOfMonth
import homepunk.github.com.presentation.core.ext.month
import homepunk.github.com.presentation.databinding.CustomLayoutTimeline2Binding
import homepunk.github.com.presentation.util.DateTimeUtil
import timber.log.Timber

/**Created by Homepunk on 09.05.2019. **/
class TimelineView<T> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    var adapter: TimelineAdapter<T>? = null
        set(value) {
            field = value
            setUpTimelineDataModels()
        }

    private fun setUpTimelineDataModels() {
        Timber.w("setUpTimelineDataModels")
        var currentMonth = -1
        var currentDay = -1

        val dateFormat = adapter!!.getDateFormat()

        with(adapter!!) {
            itemList!!.forEachIndexed { index, item ->
                val date = dateFormat.parse(getDate(item))
                val day = date.dayOfMonth()
                val month = date.month()
                val monthNum = DateTimeUtil.getMonthForInt(date.month())

                val isNewMonth = month != -1 && month != currentMonth
                if (isNewMonth) {
                    currentMonth = month
                }

                val isNewDay = currentDay != Integer.valueOf(day)
                currentDay = Integer.valueOf(day)

                with(getTimelineLayout()) {
                    model = TimelineDataModel(day.toString(), monthNum, isNewDay, isNewMonth, index == 0, index == itemList!!.lastIndex)

                    with(onInflateLayout(context, cvItem)) {
                        onBindLayout(this, index)
                    }

                    addView(root)
                }
            }
        }
    }

    private fun getTimelineLayout(): CustomLayoutTimeline2Binding {
        return CustomLayoutTimeline2Binding.inflate(LayoutInflater.from(context), this, false)
    }
}
