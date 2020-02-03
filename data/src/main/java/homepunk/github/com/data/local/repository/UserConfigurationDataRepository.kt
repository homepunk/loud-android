package homepunk.github.com.data.local.repository

import androidx.annotation.WorkerThread
import homepunk.github.com.data.local.UserConfigurationDao
import homepunk.github.com.data.local.room.model.EntityDataMapper
import homepunk.github.com.data.local.room.model.configuration.UserConfigurationEntity
import homepunk.github.com.data.util.runInBackground
import homepunk.github.com.domain.model.internal.UserConfiguration
import homepunk.github.com.domain.model.internal.CityLocation
import homepunk.github.com.domain.repository.UserConfigurationRepository
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 11.07.2019. **/
class UserConfigurationDataRepository @Inject constructor(private val userConfigurationDao: UserConfigurationDao) : UserConfigurationRepository {

    @WorkerThread
    override fun saveUserConfiguration(configuration: UserConfiguration) {
        Timber.w("NEW DB UPDATE, SAVE CONFIGURATION: id = ${configuration.userId}, lcoations count = ${configuration.locations.size}}")
        runInBackground {
            saveUserConfiguration(EntityDataMapper.map(configuration))
        }
    }

    @WorkerThread
    private fun saveUserConfiguration(configuration: UserConfigurationEntity) {
        Timber.w("NEW DB UPDATE, SAVE CONFIGURATION: id = ${configuration.user.id}, lcoations count = ${configuration.locations.size}}")
        runInBackground {
            with(configuration) {
                userConfigurationDao.insert(user)
                locations.forEach(userConfigurationDao::insert)
                filters.forEach(userConfigurationDao::insert)
            }
        }
    }

    @WorkerThread
    override fun updateUserConfiguration(configuration: UserConfiguration) {
        Timber.w("NEW DB UPDATE, UPDATE CONFIGURATION: id = ${configuration.userId}, lcoations count = ${configuration.locations.size}}")
        runInBackground {
            updateUserConfiguration(EntityDataMapper.map(configuration))
        }
    }

    @WorkerThread
    private fun updateUserConfiguration(configuration: UserConfigurationEntity) {
        Timber.w("NEW DB UPDATE, UPDATE CONFIGURATION: id = ${configuration.user.id}, lcoations count = ${configuration.locations.size}}")
        runInBackground {
            with(configuration) {
                userConfigurationDao.update(user)
                locations.forEach(userConfigurationDao::update)
                filters.forEach(userConfigurationDao::update)
            }
        }
    }

    override fun getUserConfiguration(id: Int): Flowable<UserConfiguration> {
        return userConfigurationDao.getUserConfiguration(id)
                .firstFromListOrDefault(UserConfigurationEntity()) { saveUserConfiguration(it) }
                .subscribeOn(Schedulers.io())
                .distinctUntilChanged()
                .map { EntityDataMapper.map(it) }
                .doOnNext { Timber.w("SEND NEXT configuration: ${(it as UserConfiguration).toString()}") }
    }

    @WorkerThread
    override fun saveUserLocation(userId: Int, location: CityLocation) {
        Timber.w("SAVE USER LOCATION")
        runInBackground {
            userConfigurationDao.insert(EntityDataMapper.map(userId, location))
        }
    }

    @WorkerThread
    override fun removeUserLocation(userId: Int, location: CityLocation) {
        Timber.w("REMOVE USER LOCATION")
        runInBackground {
            userConfigurationDao.delete(EntityDataMapper.map(userId, location))
        }
    }

    @WorkerThread
    override fun updateUserLocation(userId: Int, location: CityLocation) {
        Timber.w("UPDATE USER LOCATION")
        runInBackground {
            userConfigurationDao.update(EntityDataMapper.map(userId, location))
        }
    }

    @WorkerThread
    override fun updateUserLocationList(userId: Int, locations: List<CityLocation>) {
        runInBackground {
            val updateAll = userConfigurationDao.updateAll(locations.map { EntityDataMapper.map(userId, it) })
            Timber.w("UPDATE LOCATIONS LIST $updateAll")
        }
    }

}

private fun <T> Flowable<List<T>>.firstFromListOrDefault(other: T, actionWithDefault: ((T) -> Unit)? = null): Flowable<T> {
    return Flowable.defer { map { if (!it.isNullOrEmpty()) it[0] else other.apply { actionWithDefault?.invoke(this) } } }
}


