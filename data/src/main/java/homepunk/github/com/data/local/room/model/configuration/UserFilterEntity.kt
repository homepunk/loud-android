package homepunk.github.com.data.local.room.model.configuration

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**Created by Homepunk on 11.07.2019. **/
@Entity
data class UserFilterEntity(@PrimaryKey(autoGenerate = true)
                            val id: Int,
                            val userId: Int) : Serializable {
    override fun equals(other: Any?): Boolean {
        return other is UserFilterEntity &&
                other.id == id
    }
}