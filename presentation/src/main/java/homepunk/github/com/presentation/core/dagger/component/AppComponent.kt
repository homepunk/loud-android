package homepunk.github.com.presentation.common.dagger.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import homepunk.github.com.presentation.VUApplication
import homepunk.github.com.presentation.common.dagger.module.*
import javax.inject.Singleton


/**Created by Homepunk on 26.12.2018. **/
@Component(modules = [
    AppModule::class,
    DataModule::class,
    ViewModelBindingModule::class,
    RepositoryBindingModule::class,
    FeatureBindingModule::class,
    AndroidSupportInjectionModule::class,
    AndroidInjectionModule::class
])
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: VUApplication)
}