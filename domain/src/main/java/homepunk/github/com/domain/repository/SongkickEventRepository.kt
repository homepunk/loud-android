package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.songkick.SongkickEvent
import io.reactivex.Observable

interface SongkickEventRepository {
    fun getUpcomingEventList(metroAreaId: Long): Observable<List<SongkickEvent>>

    fun getEventDetails(eventId: Long): Observable<SongkickEvent>
}
