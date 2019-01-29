package homepunk.github.com.presentation.feature.discover.event.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.core.ext.dayOfMonth
import homepunk.github.com.presentation.core.ext.month
import homepunk.github.com.presentation.util.SongkickUtil

/**Created by Homepunk on 23.01.2019. **/
class EventModel(var event: SongkickEvent) : BaseObservable() {
    var date = ObservableField<String>()
    var title = ObservableField<String>()
    var location = ObservableField<String>()
    var month = ObservableInt()

    init {
        date.set(getDate(event.start?.date))
        month.set(getMonth(event.start?.date))
        title.set(getTitle2(event.displayName))
        location.set(getLocationname(event.displayName))
    }

    private fun getLocationname(value: String): String {
        return getTitle(value).run {
            if (indexOf("at") != -1)
                substring(indexOf("at"))
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
    }
}

