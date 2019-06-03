package homepunk.github.com.data.local.repository

import homepunk.github.com.data.local.SharedPreferencesManager
import homepunk.github.com.data.local.SharedPreferencesManager.Companion.KEY_CURRENT_APP_MODE
import homepunk.github.com.data.local.SharedPreferencesValueObservable
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.domain.repository.AppModeRepository
import io.reactivex.Observable
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
class AppModeDataRepository @Inject constructor(val prefsManager: SharedPreferencesManager) : AppModeRepository {
    private val modeObservable = SharedPreferencesValueObservable(prefsManager, KEY_CURRENT_APP_MODE, 2)

    override fun getCurrentMode(): Observable<AppMode> {
        return modeObservable.valueObservable
                .doOnNext { Timber.e("CURRENT MODE = $it") }
                .map { AppMode.values().find { mode -> mode.ordinal == it }!! }
    }

    override fun changeMode(mode: AppMode) {
        prefsManager.put(KEY_CURRENT_APP_MODE, mode.ordinal)
    }
}