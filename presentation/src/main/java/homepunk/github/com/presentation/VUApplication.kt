package homepunk.github.com.presentation

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import homepunk.github.com.presentation.core.dagger.component.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class VUApplication : Application(), HasSupportFragmentInjector, HasActivityInjector {
    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun activityInjector() = activityInjector

}