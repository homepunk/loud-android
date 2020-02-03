package homepunk.github.com.data.local.room.model

import homepunk.github.com.data.local.room.model.configuration.UserConfigurationEntity
import homepunk.github.com.data.local.room.model.configuration.UserEntity
import homepunk.github.com.data.local.room.model.configuration.UserFilterEntity
import homepunk.github.com.data.local.room.model.configuration.UserLocationEntity
import homepunk.github.com.domain.model.internal.UserConfiguration
import homepunk.github.com.domain.model.internal.UserFilter
import homepunk.github.com.domain.model.internal.CityLocation

/**Created by Homepunk on 11.07.2019. **/
object EntityDataMapper {
    fun map(fromModel: UserConfiguration): UserConfigurationEntity {
        return UserConfigurationEntity().apply {
            user = UserEntity(fromModel.userId)
            locations = fromModel.locations.map { map(fromModel.userId, it) }
            filters = fromModel.filters.map { map(fromModel.userId, it) }
        }
    }

    fun map(userId: Int, fromModel: CityLocation): UserLocationEntity {
        return UserLocationEntity(fromModel.id, userId, fromModel.locationName, fromModel.countryName, fromModel.isCurrent)
    }

    fun map(userId: Int, fromModel: UserFilter): UserFilterEntity {
        return UserFilterEntity(fromModel.id, userId)
    }

    fun map(fromModel: UserConfigurationEntity): UserConfiguration {
        return UserConfiguration(
                fromModel.user.id,
                fromModel.locations.map { map(it) }.toMutableList(),
                fromModel.filters.map { map(it) }.toMutableList())
    }

    fun map(fromModel: UserLocationEntity): CityLocation {
        return CityLocation(fromModel.id, fromModel.locationName, fromModel.countryName, fromModel.isCurrent)
    }

    fun map(fromModel: UserFilterEntity): UserFilter {
        return UserFilter(fromModel.id)
    }
}