package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.internal.UserConfiguration
import homepunk.github.com.domain.model.internal.CityLocation
import homepunk.github.com.domain.repository.UserConfigurationRepository
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

/**Created by Homepunk on 11.07.2019. **/

@Singleton
class UserConfigurationInteractor @Inject constructor(var userConfigurationRepository: UserConfigurationRepository) {
    private var userConfiguration: UserConfiguration? = null

    private var userConfigurationBehaviorSubject: BehaviorSubject<UserConfiguration>? = null

    fun saveUserConfiguration(configuration: UserConfiguration) {
        userConfigurationRepository.saveUserConfiguration(configuration)
    }

    fun updateUserConfiguration(configuration: UserConfiguration) {
        userConfigurationRepository.updateUserConfiguration(configuration)
    }

    fun getUserConfiguration(id: Int): Observable<UserConfiguration> {
        if (userConfigurationBehaviorSubject == null) {
            userConfigurationBehaviorSubject = BehaviorSubject.create()
            userConfigurationRepository.getUserConfiguration(id)
                    .flatMap {
                        if (it.locations.isNotEmpty()) {
                            for (location in it.locations) {
                                if (location.isCurrent) {
                                    return@flatMap Flowable.just(it)
                                }
                            }
                            userConfigurationRepository.updateUserLocation(it.userId, it.locations[0].apply { isCurrent = true })
                            Flowable.empty()
                        } else {
                            Flowable.just(it)
                        }
                    }
                    .doOnNext { userConfiguration = it }
                    .toObservable()
                    .subscribe(userConfigurationBehaviorSubject!!)
        }

        return userConfigurationBehaviorSubject!!
    }

    fun getUserLocationList(): Observable<MutableList<CityLocation>> {
        return getUserConfiguration(0)
                .filter { it.locations.isNotEmpty() }
                .map { it.locations }


    }

    fun getUserCurrentLocation(): Observable<CityLocation> {
        return getUserConfiguration(0)
                .flatMapIterable { it.locations }
                .filter { it.isCurrent }
    }

    fun addUserLocation(location: CityLocation) {
        userConfiguration?.run {
            userConfigurationRepository.saveUserLocation(userId, location)
        }
    }

    fun removeUserLocation(location: CityLocation) {
        userConfiguration?.run {
            userConfigurationRepository.removeUserLocation(userId, location)
        }
    }

    fun updateUserLocation(location: CityLocation) {
        userConfiguration?.run {
            userConfigurationRepository.updateUserLocation(userId, location)
        }
    }

    fun updateUserLocationList(location: List<CityLocation>) {
        userConfiguration?.run {
            userConfigurationRepository.updateUserLocationList(userId, location)
        }
    }
}