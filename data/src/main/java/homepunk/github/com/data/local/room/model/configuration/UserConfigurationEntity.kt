package homepunk.github.com.data.local.room.model.configuration

import androidx.room.Embedded
import androidx.room.Relation

/**Created by Homepunk on 11.07.2019. **/
class UserConfigurationEntity {
    @Embedded
    var user: UserEntity = UserEntity(0)

    @Relation(parentColumn = "id", entityColumn = "userId", entity = UserLocationEntity::class)
    var locations: List<UserLocationEntity> = listOf()

    @Relation(parentColumn = "id", entityColumn = "userId", entity = UserFilterEntity::class)
    var filters: List<UserFilterEntity> = listOf()

    override fun equals(other: Any?): Boolean {
        return other is UserConfigurationEntity &&
                other.user == user &&
                other.filters == filters &&
                other.locations == locations
    }

}