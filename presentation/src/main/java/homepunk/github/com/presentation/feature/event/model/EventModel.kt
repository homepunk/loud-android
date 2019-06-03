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
        date.set(getDate(event.start?.date))
        month.set(getMonth(event.start?.date))
        fullDate.set(date.get() + " " + DateTimeUtil.getMonthForInt(month.get()))
        title.set(getTitle2(event.displayName))
        val fromTodayPair = DateTimeUtil.getDurationFromTodayPair(event.start!!.date!!)
        location.set(getLocationName(event.displayName))
        val inMillis = DateTimeUtil.format.parse(event.start!!.date!!).time
        val timeInMillis = System.currentTimeMillis() - inMillis
        val currentCalendar = Calendar.getInstance(Locale.ENGLISH)
        currentCalendar.time = Date(inMillis)
        var startExpire = StringBuilder()
                .append("Finishes in :")

                if(currentCalendar.get(Calendar.WEEK_OF_MONTH)== 0) {
                    startExpire.append(currentCalendar.get(Calendar.DAY_OF_MONTH).toString()+" days")
                } else {
                    startExpire.append(currentCalendar.get(Calendar.WEEK_OF_MONTH).toString()+ " weeks")
                }
        startFromToday.set(startExpire.toString())

        /*val currentCalendar = Calendar.getInstance(Locale(Utils.languageCode))

                     if((System.currentTimeMillis()-calendar.timeInMillis) > 0) {
                         if((System.currentTimeMillis()-calendarEnd.timeInMillis) > 0) {
                         } else {
                             currentCalendar.timeInMillis = System.currentTimeMillis() - calendarEnd.timeInMillis
                             startExpire  +=  "Finishes in :"
                             startExpire += if(currentCalendar.get(Calendar.WEEK_OF_MONTH)==0) {
                                 currentCalendar.get(Calendar.DAY_OF_MONTH).toString()+" days"
                             } else {
                                 currentCalendar.get(Calendar.WEEK_OF_MONTH).toString()+" weeks"
                             }
                         }
                     } else {
                         currentCalendar.timeInMillis = System.currentTimeMillis() - calendar.timeInMillis
                         startExpire  +=  "Starts in :"
                         startExpire += if(currentCalendar.get(Calendar.WEEK_OF_MONTH)==0) {
                             currentCalendar.get(Calendar.DAY_OF_MONTH).toString()+" days"
                         } else {
                             currentCalendar.get(Calendar.WEEK_OF_MONTH).toString()+" weeks"
                         }
                     }*/
    }

    private fun getLocationName(value: String): String {
        return getTitle(value).run {
            if (indexOf("at ") != -1)
                substring(indexOf("at ") + 3)
            else
                "Unknown"
        }
    }

    @Bindable
    fun getThumb(): String {
        return SongkickUtil.getSongkickArtistThumb(event.performance[0].artist?.id?.toString())
    }

    private fun getTitle2(value: String): String {
        return value.run {
            if (indexOf("at") != -1)
                substring(0, indexOf("at"))
            else if (indexOf("(") != -1)
                substring(0, indexOf("("))
            else
                this
        }
    }

    private fun getTitle(value: String): String {
        return value.run {
            if (indexOf("(") != -1)
                substring(0, indexOf("("))
            else
                this
        }
    }

    private fun getDate(value: String?): String {
        val sb = StringBuilder()
        if (!value.isNullOrEmpty()) {
            val dayOfMonth = SongkickUtil.getDate(value).dayOfMonth()
            if (dayOfMonth < 10) sb.append(0)
            sb.append(dayOfMonth)
        } else {
            sb.append("?")
        }
        return sb.toString()
    }

    private fun getMonth(value: String?): Int {
        event.start?.let {
            return if (!it.date.isNullOrEmpty()) {
                SongkickUtil.getDate(it.date!!).month()
            } else {
                -1
            }
        }
        return -1
    }
}

