package homepunk.github.com.vinyl_underground.common.dagger

import android.app.Application
import homepunk.github.com.data.remote.DiscogsApi
import homepunk.github.com.data.repository.DiscogsDataRepository

//@Module
class AppModule(private var mApplication: Application) {

//    @Provides
//    @Singleton
    fun providesApplication(): Application {
        return mApplication
    }

//    @Provides
//    @Singleton
    fun provideDiscogsDataRepository(discogsApi: DiscogsApi) = DiscogsDataRepository(discogsApi)
}