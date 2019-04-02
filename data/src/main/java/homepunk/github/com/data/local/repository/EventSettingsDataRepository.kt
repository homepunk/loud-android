package homepunk.github.com.data.local.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import homepunk.github.com.data.local.SharedPreferencesManager
import homepunk.github.com.data.local.SharedPreferencesManager.Companion.KEY_COUNTRY_CITIES
import homepunk.github.com.data.local.SharedPreferencesManager.Companion.KEY_CURRENT_COUNTRY
import homepunk.github.com.data.local.SharedPreferencesValueObservable
import homepunk.github.com.domain.model.internal.UserEventLocationSettings
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.EventSettingsRepository
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 29.03.2019. **/
class EventSettingsDataRepository @Inject constructor(val prefsManager: SharedPreferencesManager) : EventSettingsRepository {


    private lateinit var locationListObservable: SharedPreferencesValueObservable<String>

    override fun saveUserCountry(countryName: String) {
        prefsManager.put(KEY_CURRENT_COUNTRY, countryName)
    }

    override fun getUserCountryName() = prefsManager.get(KEY_CURRENT_COUNTRY, "UKRAINE")

    override fun saveUserLocationForCountry(countryName: String, location: SongkickLocation) {
        val key = KEY_COUNTRY_CITIES.postfix(countryName)
        val currentLocationList = getCurrentLocationList(key)
        currentLocationList.add(location)

        Timber.w("SAVE TO PREFS: ${location.city?.displayName}")
        prefsManager.put(key, Gson().toJson(currentLocationList))
    }

    override fun removeUserLocationForCountry(countryName: String, location: SongkickLocation) {
        val key = KEY_COUNTRY_CITIES.postfix(countryName)
        val currentLocationList = getCurrentLocationList(key)
        Timber.w("REMOVE FROM PREFS: ${location.city?.displayName}, ${currentLocationList.remove(location)}")
        prefsManager.put(key, Gson().toJson(currentLocationList))
    }

    override fun getUserLocationListForCountry(countryName: String): Observable<UserEventLocationSettings> {
        locationListObservable = SharedPreferencesValueObservable(prefsManager, KEY_COUNTRY_CITIES.postfix(countryName), "")

        return locationListObservable.valueObservable
                .map { getSongkickLocationList(it) }
                .doOnNext { Timber.w("UPDATE CITIES COUNT ${it.size}") }
                .map { UserEventLocationSettings(countryName, it) }
    }

    private fun getCurrentLocationList(key: String): ArrayList<SongkickLocation> {
        val locationListJson = prefsManager.get(key, "")
        val locationList = getSongkickLocationList(locationListJson)
        Timber.w("GET FROM PREFS: $locationListJson")
        return locationList
    }

    private fun getSongkickLocationList(locationListJson: String): ArrayList<SongkickLocation> {
        val listType = object : TypeToken<List<SongkickLocation>>() {}.type
        return if (locationListJson.isEmpty())
            arrayListOf()
        else
            Gson().fromJson<ArrayList<SongkickLocation>>(locationListJson, listType)
    }
}

private fun String.postfix(value: String): String {
    return plus("_").plus(value.toUpperCase())
}
