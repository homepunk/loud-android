package homepunk.github.com.presentation.common.dagger.module

import android.content.Context
import dagger.Module
import dagger.Provides
import homepunk.github.com.data.remote.ApiClient
import homepunk.github.com.data.remote.DiscogsApi
import homepunk.github.com.data.remote.SongkickApi
import homepunk.github.com.presentation.common.data.AppDataFactory
import javax.inject.Singleton

/**Created by Homepunk on 27.12.2018. **/
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDiscogsApi(): DiscogsApi = ApiClient.Builder.buildDiscogsApi()

    @Provides
    @Singleton
    fun provideSongkickApi(): SongkickApi = ApiClient.Builder.buildSongkickApi()
}