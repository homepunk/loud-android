package homepunk.github.com.presentation.feature.discover.event.upcoming

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.util.SongkickUtil

/**Created by Homepunk on 23.01.2019. **/
class EventModel(var event: SongkickEvent) : BaseObservable() {
    @Bindable
    fun getThumb(): String {
        return SongkickUtil.getSongkickArtistThumb(event.performance[0].artist?.id?.toString())
    }

    @Bindable
    fun getTitle(): String {
        return event.displayName
    }
}

