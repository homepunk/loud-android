package homepunk.github.com.presentation.core.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import homepunk.github.com.data.local.UserConfigurationDao
import homepunk.github.com.data.local.room.UserDatabase
import javax.inject.Singleton

/**Created by Homepunk on 11.07.2019. **/
@Module
class LocalDataModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): UserDatabase {
        return UserDatabase.getDatabase(context)
    }


    @Provides
    @Singleton
    fun provideUserConfigurationDao(database: UserDatabase) : UserConfigurationDao {
        return database.userConfigurationDao()
    }
}