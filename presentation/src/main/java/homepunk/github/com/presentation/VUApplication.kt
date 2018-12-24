package homepunk.github.com.presentation

import android.app.Application
import homepunk.github.com.presentation.common.dagger.AppModule
import homepunk.github.com.presentation.common.dagger.DaggerAppComponent
import homepunk.github.com.presentation.common.dagger.DataModule

class VUApplication : Application() {
    lateinit var appComponent: DaggerAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .dataModule(DataModule())
                .build() as DaggerAppComponent
        appComponent.inject(this)
    }
}