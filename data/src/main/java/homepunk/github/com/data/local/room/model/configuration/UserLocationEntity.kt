package homepunk.github.com.data.local.room.model.configuration

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.io.Serializable

/**Created by Homepunk on 11.07.2019. **/
@Entity(indices = [Index(value = ["locationName"], unique = true)])
class UserLocationEntity(@PrimaryKey val id: Long,
                         val userId: Int,
                         val locationName: String,
                         val countryName: String,
                         val isCurrent: Boolean) : Serializable {
    override fun equals(other: Any?): Boolean {
        return (other is UserLocationEntity) &&
                other.id == id &&
                other.isCurrent == isCurrent &&
                other.locationName == locationName
    }
}