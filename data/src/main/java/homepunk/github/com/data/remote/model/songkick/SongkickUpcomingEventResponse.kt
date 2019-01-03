package homepunk.github.com.data.remote.model.songkick

import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.domain.model.songkick.SongkickLocation

data class SongkickUpcomingEventResponse (var event: List<SongkickEvent> = mutableListOf())
