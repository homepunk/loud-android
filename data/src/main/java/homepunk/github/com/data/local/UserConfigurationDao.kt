package homepunk.github.com.data.local

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import homepunk.github.com.data.local.room.model.configuration.UserConfigurationEntity
import homepunk.github.com.data.local.room.model.configuration.UserEntity
import homepunk.github.com.data.local.room.model.configuration.UserFilterEntity
import homepunk.github.com.data.local.room.model.configuration.UserLocationEntity
import io.reactivex.Flowable

/**Created by Homepunk on 11.07.2019. **/
@Dao
interface UserConfigurationDao {
    @Insert(onConflict = REPLACE)
    fun insert(location: UserLocationEntity)

    @Insert(onConflict = REPLACE)
    fun insert(user: UserEntity)

    @Insert(onConflict = REPLACE)
    fun insert(filter: UserFilterEntity)

    @Update
    fun update(user: UserEntity)

    @Update
    fun update(location: UserLocationEntity)

    @Update
    fun updateAll(locations: List<UserLocationEntity>) : Int

    @Update
    fun update(filter: UserFilterEntity)

    @Delete
    fun delete(location: UserLocationEntity)

    @Query("SELECT * FROM UserEntity WHERE id =:userId")
    fun getUserConfiguration(userId: Int) : Flowable<List<UserConfigurationEntity>>

    @Query("SELECT * FROM UserLocationEntity WHERE userId =:userId")
    fun getUserLocations(userId: Int) : Flowable<List<UserLocationEntity>>
}