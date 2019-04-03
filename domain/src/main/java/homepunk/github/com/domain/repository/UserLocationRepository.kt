package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.domain.model.songkick.SongkickLocation
import io.reactivex.Observable

/**Created by Homepunk on 29.03.2019. **/
interface UserLocationRepository {
    fun saveUserCountry(countryName: String)

    fun getUserCountryName(): String

    fun saveUserLocationForCountry(countryName: String, location: SongkickLocation)

    fun removeUserLocationForCountry(countryCode: String, location: SongkickLocation)

    fun getUserLocationListForCountry(countryName: String): Observable<UserLocation>
}