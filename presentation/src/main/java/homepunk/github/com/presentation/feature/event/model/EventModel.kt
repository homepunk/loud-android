package homepunk.github.com.presentation.feature.event.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.core.ext.dayOfMonth
import homepunk.github.com.presentation.core.ext.month
import homepunk.github.com.presentation.util.DateTimeUtil
import homepunk.github.com.presentation.util.SongkickUtil
import timber.log.Timber
import java.io.Serializable
import java.util.*

/**Created by Homepunk on 23.01.2019. **/
class EventModel(var event: SongkickEvent) : Serializable, BaseObservable() {
    var month = ObservableInt()
    var date = ObservableField<String>()
    var startFromToday = ObservableField<String>()
    var fullDate = ObservableField<String>()
    var title = ObservableField<String>()
    var location = ObservableField<String>()

    init {
        Timber.w("THREAD = ${Thread.currentThread().name}, item name = ${event.displayName}")
        if (!event.start?.date.isNullOrEmpty()) {
            val startDate = SongkickUtil.getDate(event.start?.date!!)
            date.set(getDate(startDate))
            month.set(getMonth(startDate))
            fullDate.set(date.get() + " " + DateTimeUtil.getMonthForInt(month.get()))
            startFromToday.set(getStartFromToday(startDate))
        }
        title.set(getEventName(event.displayName))
        location.set(getLocationName(event.displayName))
    }

    @Bindable
    fun getThumb(): String {
        return SongkickUtil.getSongkickArtistThumb(event.performance[0].artist?.id?.toString())
    }

    private fun getLocationName(value: String): String {
        return getTitleWithoutDate(value).run {
            if (indexOf("at ") != -1)
                substring(indexOf("at ") + 3)
            else
                "Unknown"
        }
    }

    private fun getEventName(value: String): String {
        return value.run {
            if (indexOf("at ") != -1)
                substring(0, indexOf("at "))
            else if (indexOf("(") != -1)
                substring(0, indexOf("("))
            else
                this
        }
    }

    private fun getTitleWithoutDate(value: String): String {
        return value.run {
            if (indexOf("(") != -1)
                substring(0, indexOf("("))
            else
                this
        }
    }

    private fun getDate(value: Date): String {
        return StringBuilder()
                .apply {
                    val dayOfMonth = value.dayOfMonth()
                    if (dayOfMonth < 10) append(0)
                    append(dayOfMonth)
                }.toString()
    }

    private fun getMonth(value: Date): Int {
        return value.month()
    }

    private fun getStartFromToday(startDate: Date): String {
        val inMillis = startDate.time
        val currentCalendar = Calendar.getInstance(Locale.ENGLISH)
        currentCalendar.time = Date(inMillis)
        var startExpire = StringBuilder()
                .append("Finishes in :")

        if (currentCalendar.get(Calendar.WEEK_OF_MONTH) == 0) {
            startExpire.append(currentCalendar.get(Calendar.DAY_OF_MONTH).toString() + " days")
        } else {
            startExpire.append(currentCalendar.get(Calendar.WEEK_OF_MONTH).toString() + " weeks")
        }
        return startExpire.toString()
    }
}

