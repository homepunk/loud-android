package homepunk.github.com.presentation.core.dagger.module

import dagger.Module
import dagger.Provides
import homepunk.github.com.data.remote.ApiClient
import homepunk.github.com.data.remote.DiscogsApi
import homepunk.github.com.data.remote.SongkickApi
import javax.inject.Singleton

/**Created by Homepunk on 27.12.2018. **/
@Module
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideDiscogsApi(): DiscogsApi = ApiClient.Builder.buildDiscogsApi()

    @Provides
    @Singleton
    fun provideSongkickApi(): SongkickApi = ApiClient.Builder.buildSongkickApi()
}