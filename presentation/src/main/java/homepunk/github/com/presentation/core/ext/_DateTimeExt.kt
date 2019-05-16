package homepunk.github.com.presentation.core.ext

import java.text.DateFormatSymbols
import java.util.*

/**Created by Homepunk on 24.01.2019. **/
fun Date.dayOfMonth(): Int {
    val cal = Calendar.getInstance();
    cal.setTime(this)
    return cal.get(Calendar.DAY_OF_MONTH)
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
