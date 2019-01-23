package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.songkick.SongkickLocation
import io.reactivex.Observable

interface LocationRepository {
    fun getSongkickLocationByQuery(query: String): Observable<SongkickLocation>

    fun getSongkickLocationByLatLng(latLng: String): Observable<SongkickLocation>
}
