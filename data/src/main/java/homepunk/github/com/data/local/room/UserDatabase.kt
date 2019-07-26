package homepunk.github.com.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import homepunk.github.com.data.local.UserConfigurationDao
import homepunk.github.com.data.local.room.model.configuration.UserEntity
import homepunk.github.com.data.local.room.model.configuration.UserFilterEntity
import homepunk.github.com.data.local.room.model.configuration.UserLocationEntity
import timber.log.Timber

/**Created by Homepunk on 11.07.2019. **/
@Database(
        entities = [
            UserEntity::class,
            UserLocationEntity::class,
            UserFilterEntity::class],
        version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userConfigurationDao(): UserConfigurationDao

    companion object {
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            Timber.w("DATABASE CREATED")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java,
                        "User_database").build()
                INSTANCE = instance
                instance
            }
        }
    }
}