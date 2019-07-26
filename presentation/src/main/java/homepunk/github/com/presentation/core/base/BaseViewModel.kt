package homepunk.github.com.presentation.core.base

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    var subscriptions: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        subscriptions.run {
            if (!isDisposed) {
                dispose()
            }
        }
    }


    fun dLog(message: String) {
        Log.d(this.javaClass.canonicalName, message)
    }

    fun wLog(message: String) {
        Log.w(this.javaClass.canonicalName, message)
    }

    fun eLog(message: String) {
        Log.e(this.javaClass.canonicalName, message)
    }
}
