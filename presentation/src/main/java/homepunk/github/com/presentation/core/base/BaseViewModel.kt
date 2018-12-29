package homepunk.github.com.presentation.core.base

import android.content.Context
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference

abstract class BaseViewModel<T>(val mContext: Context) : ViewModel() {
    private lateinit var weakReference: WeakReference<T>
    var compositeDisposable: CompositeDisposable? = null

    init {
        compositeDisposable = CompositeDisposable()
        init()
    }

    open fun init() {

    }

    fun bind(view: T) {
        weakReference = WeakReference(view)
    }

    fun unbind() {
        weakReference.clear()
    }

    open fun isViewExisted(): Boolean {
        if (weakReference.get() == null)
            return false
        return true
    }

    open fun getView(): T? {
        return weakReference.get()
    }
}
