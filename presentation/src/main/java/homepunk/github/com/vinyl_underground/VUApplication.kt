package homepunk.github.com.vinyl_underground

import android.app.Application
import homepunk.github.com.vinyl_underground.common.dagger.DataComponent
import homepunk.github.com.data.common.dagger.DataModule

class VUApplication : Application() {
   /* val dataComponent: DaggerDataComponent by lazy {
        DaggerDataComponent
                .builder()
                .dataModule(DataModule())
                .build()
    }*/
    override fun onCreate() {
        super.onCreate()
//        dataComponent.inject(this)
    }
}