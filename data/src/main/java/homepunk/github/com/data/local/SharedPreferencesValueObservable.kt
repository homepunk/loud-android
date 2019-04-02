package homepunk.github.com.data.local

import android.content.SharedPreferences
import io.reactivex.Observable
import timber.log.Timber

/**Created by Homepunk on 18.01.2019. **/
class SharedPreferencesValueObservable<T>(val prefsManager: SharedPreferencesManager, val key: String, val defValue: T) {
    init {
        Timber.w("SharedPreferencesValueObservable CREATED: $key")
    }

    val valueObservable: Observable<T> = Observable.create { emitter ->
        val mListener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            Timber.e("onPrefsChanged = $key, KEY ${this.key}")
            if (key == this.key) {
                Timber.e("onNext = $key, KEY ${this.key}")
                emitter.onNext(prefsManager.get(this.key, defValue))
            }
        }

        emitter.setCancellable {
            Timber.e("onCancelable")
            prefsManager.prefs.unregisterOnSharedPreferenceChangeListener(mListener)
        }
        emitter.onNext(prefsManager.get(key, defValue))
        prefsManager.prefs.registerOnSharedPreferenceChangeListener(mListener)
    }
}
