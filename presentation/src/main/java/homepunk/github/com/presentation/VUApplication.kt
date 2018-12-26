package homepunk.github.com.presentation

import android.app.Application
import homepunk.github.com.presentation.common.dagger.AppModule
import homepunk.github.com.data.common.dagger.DataModule
import homepunk.github.com.presentation.common.dagger.DaggerAppComponent

class VUApplication : Application() {
    lateinit var appComponent: DaggerAppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build() as DaggerAppComponent
        appComponent.newDataComponent(DataModule())
    }
}