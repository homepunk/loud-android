package homepunk.github.com.presentation.util

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**Created by Homepunk on 24.01.2019. **/
object DateTimeUtil {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)

    fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months = dfs.months
        if (num in 0..11) {
            month = months[num]
        }
        return month
    }

    fun getDurationPair(fromInMillis: Long, toInMillis: Long): Pair<Int, Int> {
        val durationInMillis = toInMillis - fromInMillis
        val duration = TimeUnit.MILLISECONDS.toDays(durationInMillis) + 1
        val weeks = duration / 7
        val days = duration % 7
        return Pair(weeks.toInt(), days.toInt())
    }

    fun getDurationFromTodayPair(toDayInMillis: Long): Pair<Int, Int> {
        return getDurationPair(System.currentTimeMillis(), toDayInMillis)
    }

    fun getDaysFromTodayPair(toDayInMillis: Long): Int {
        val durationInMillis = toDayInMillis - System.currentTimeMillis()
        val duration = TimeUnit.MILLISECONDS.toDays(durationInMillis) + 1
        return duration.toInt()

    }

    fun getDurationFromTodayPair(toDay: String): Pair<Int, Int> {
        try {
            val endDateInMillis = format.parse(toDay).time
            return getDurationPair(System.currentTimeMillis(), endDateInMillis)

        } catch (exception: Exception) {
            return Pair(0, 0)
        }
    }
    fun getDaysFromTodayPair(toDay: String): Int {
        try {
            val endDateInMillis = format.parse(toDay).time
            return getDaysFromTodayPair(endDateInMillis)

        } catch (exception: Exception) {
            return 0
        }
    }

}