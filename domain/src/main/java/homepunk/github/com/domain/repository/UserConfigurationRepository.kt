package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.internal.UserConfiguration
import homepunk.github.com.domain.model.internal.UserLocation
import io.reactivex.Flowable

interface UserConfigurationRepository {
    fun saveUserConfiguration(configuration: UserConfiguration)

    fun updateUserConfiguration(configuration: UserConfiguration)

    fun saveUserLocation(userId: Int, location: UserLocation)

    fun removeUserLocation(userId: Int, location: UserLocation)

    fun updateUserLocation(userId: Int, location: UserLocation)

    fun updateUserLocationList(userId: Int, locations: List<UserLocation>)

    fun getUserConfiguration(id: Int): Flowable<UserConfiguration>
}
