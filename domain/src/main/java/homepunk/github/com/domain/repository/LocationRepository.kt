package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.songkick.SongkickLocation
import io.reactivex.Observable
import io.reactivex.Single

interface LocationRepository {
    fun getSongkickLocation(query: String): Observable<SongkickLocation>
}
