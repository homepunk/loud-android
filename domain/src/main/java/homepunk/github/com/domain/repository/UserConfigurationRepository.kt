package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.internal.UserConfiguration
import homepunk.github.com.domain.model.internal.CityLocation
import io.reactivex.Flowable

interface UserConfigurationRepository {
    fun saveUserConfiguration(configuration: UserConfiguration)

    fun updateUserConfiguration(configuration: UserConfiguration)

    fun saveUserLocation(userId: Int, location: CityLocation)

    fun removeUserLocation(userId: Int, location: CityLocation)

    fun updateUserLocation(userId: Int, location: CityLocation)

    fun updateUserLocationList(userId: Int, locations: List<CityLocation>)

    fun getUserConfiguration(id: Int): Flowable<UserConfiguration>
}
