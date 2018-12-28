package homepunk.github.com.presentation.core.dagger.module

import dagger.Module
import dagger.Provides
import homepunk.github.com.data.remote.DiscogsApi
import homepunk.github.com.data.remote.DiscogsApiBuilder
import javax.inject.Singleton

/**Created by Homepunk on 27.12.2018. **/
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideDiscogsApi(): DiscogsApi = DiscogsApiBuilder().build()
}