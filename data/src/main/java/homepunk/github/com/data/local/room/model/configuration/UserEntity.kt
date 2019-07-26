package homepunk.github.com.data.local.room.model.configuration

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/**Created by Homepunk on 12.07.2019. **/
@Entity
data class UserEntity(@PrimaryKey(autoGenerate = false) val id: Int) : Serializable {
}