package homepunk.github.com.domain.model.internal

import java.io.Serializable

/**Created by Homepunk on 11.07.2019. **/
data class UserConfiguration(val userId: Int,
                             val locations: MutableList<UserLocation>,
                             val filters: MutableList<UserFilter>
) : Serializable {

    constructor() : this(0, mutableListOf(), mutableListOf())

    override fun equals(other: Any?): Boolean {
        return other is UserConfiguration &&
                other.userId == userId &&
                other.filters == filters &&
                other.locations == locations
    }

    override fun toString(): String {
        return "[userId = $userId, locations = $locations, filters = $filters]"
    }
}