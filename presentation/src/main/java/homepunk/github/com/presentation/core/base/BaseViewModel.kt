package homepunk.github.com.presentation.core.base

import android.util.Log
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {
    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        init()
    }

    open fun init() {

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.run {
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
