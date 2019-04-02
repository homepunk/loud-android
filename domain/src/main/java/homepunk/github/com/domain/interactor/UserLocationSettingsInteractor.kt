package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.internal.UserEventLocationSettings
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.EventSettingsRepository
import homepunk.github.com.domain.repository.LocationRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**Created by Homepunk on 29.03.2019. **/
@Singleton
class UserLocationSettingsInteractor @Inject constructor(var locationRepository: LocationRepository,
                                                         var userSettingsRepository: EventSettingsRepository) {

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

    fun getCurrentUserLocationSettings(): Observable<UserEventLocationSettings> {
        return userSettingsRepository.getUserLocationListForCountry(userSettingsRepository.getUserCountryName())
                .doOnNext {
                    currentUserLocationList = it.currentUserLocationList as ArrayList<SongkickLocation>
                }
    }

    fun getCountryUserLocationSettings(countryName: String): Observable<UserEventLocationSettings> {
        return userSettingsRepository.getUserLocationListForCountry(countryName)
                .doOnNext { userCountryLocationsMap[countryName] = it.currentUserLocationList as ArrayList<SongkickLocation> } }
}