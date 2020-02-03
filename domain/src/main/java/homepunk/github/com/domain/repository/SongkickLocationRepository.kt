package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.internal.CityLocation
import io.reactivex.Observable

interface SongkickLocationRepository {
    fun getSongkickLocationByQuery(query: String): Observable<CityLocation>

    fun getSongkickLocationByLatLng(latLng: String): Observable<CityLocation>
}
