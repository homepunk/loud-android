package homepunk.github.com.presentation.util

import java.text.DateFormatSymbols

/**Created by Homepunk on 24.01.2019. **/
object DateTimeUtil {
    fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months = dfs.getMonths()
        if (num >= 0 && num <= 11) {
            month = months[num]
        }
        return month
    }
}