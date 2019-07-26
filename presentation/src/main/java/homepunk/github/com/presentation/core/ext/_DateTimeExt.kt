package homepunk.github.com.presentation.core.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.util.DateTimeUtil
import java.text.DateFormatSymbols
import java.util.*

/**Created by Homepunk on 24.01.2019. **/
fun Date.dayOfMonth(): Int {
    val cal = Calendar.getInstance();
    cal.setTime(this)
    return cal.get(Calendar.DAY_OF_MONTH)
}


fun Date.dayOfWeek(): String {
    val cal = Calendar.getInstance();
    cal.time = this
    return DateFormatSymbols.getInstance().weekdays[cal.get(Calendar.DAY_OF_WEEK)]
}

fun Date.month(): Int {
    val cal = Calendar.getInstance()
    cal.setTime(this)
    return cal.get(Calendar.MONTH)
}

fun Date.year(): Int {
    val cal = Calendar.getInstance();
    cal.setTime(this)
    return cal.get(Calendar.YEAR)
}

fun Date.monthName(): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months = dfs.months
        val num = month()
        if (num in 0..11) {
            month = months[num]
        }
        return month
}


@BindingAdapter("monthForInt")
fun TextView.bindMonthForInt(value: Int) {
    text = DateTimeUtil.getMonthForInt(value).substring(0, 3).plus(".")
}
