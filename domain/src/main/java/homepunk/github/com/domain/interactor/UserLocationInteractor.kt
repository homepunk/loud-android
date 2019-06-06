package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.LocationRepository
import homepunk.github.com.domain.repository.UserLocationRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**Created by Homepunk on 29.03.2019. **/
@Singleton
class UserLocationInteractor @Inject constructor(var locationRepository: LocationRepository,
                                                 var userSettingsRepository: UserLocationRepository) {

    private lateinit var currentUserLocationList: ArrayList<SongkickLocation>
    private var userCountryLocationsMap = hashMapOf<String, ArrayList<SongkickLocation>>()

    fun saveUserLocationForCountry(countryName: String, location: SongkickLocation) {
        if (!userCountryLocationsMap[countryName].isNullOrEmpty() &&
                userCountryLocationsMap[countryName]!!.contains(location)) {
            userSettingsRepository.removeUserLocationForCountry(countryName, location)
            userCountryLocationsMap[countryName]!!.remove(location)
        } else {
            userSettingsRepository.saveUserLocationForCountry(countryName, location)
            userCountryLocationsMap[countryName]!!.add(location)
        }

    }

    fun getCountryUserLocation(countryName: String): Observable<UserLocation> {
        return userSettingsRepository.getUserLocationListForCountry(countryName)
                .doOnNext { userCountryLocationsMap[countryName] = it.locations as ArrayList<SongkickLocation> } }

    fun getCurrentUserLocation(): Observable<UserLocation> {
        return userSettingsRepository.getUserLocationListForCountry(userSettingsRepository.getUserCountryName())
                .doOnNext {
                    currentUserLocationList = it.locations as ArrayList<SongkickLocation>
                }
    }
}