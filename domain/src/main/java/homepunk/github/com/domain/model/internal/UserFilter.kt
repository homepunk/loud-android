package homepunk.github.com.domain.model.internal

import java.io.Serializable

/**Created by Homepunk on 11.07.2019. **/
data class UserFilter(val id: Int) : Serializable {
    override fun equals(other: Any?): Boolean {
        return other is UserFilter &&
                other.id == id
    }
}