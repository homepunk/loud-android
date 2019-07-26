package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.internal.UserLocation
import io.reactivex.Observable

interface SongkickLocationRepository {
    fun getSongkickLocationByQuery(query: String): Observable<UserLocation>

    fun getSongkickLocationByLatLng(latLng: String): Observable<UserLocation>
}
