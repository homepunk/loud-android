package homepunk.github.com.presentation.feature.discover.event.upcoming

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.util.SongkickUtil

/**Created by Homepunk on 11.01.2019. **/
class EventLocationModel(locationName: String? = "", eventList: List<SongkickEvent> = arrayListOf()) : ViewModel() {
    val locationName = ObservableField<String>(locationName)
    val events = ObservableArrayList<EventModel>()
    val isParentExpanded = ObservableBoolean(true)
    val onParentClickListener = View.OnClickListener { isParentExpanded.swap() }

    init {
        eventList.forEach {
            events.add(EventModel(it.thumb(), it.displayName))
        }
    }
//    var thumb = ObservableField<String>(SongkickUtil.getSongkickArtistThumb(imageUrl))

    fun SongkickEvent.thumb() = SongkickUtil.getSongkickArtistThumb(performance[0].artist?.id?.toString())

    fun ObservableBoolean.swap() = set(!get())
}