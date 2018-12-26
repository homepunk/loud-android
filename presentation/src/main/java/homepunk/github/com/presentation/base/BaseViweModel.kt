package homepunk.github.com.presentation.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.BaseObservable
import java.lang.ref.WeakReference

abstract class BaseViweModel(mContext: Context) : ViewModel() {
    val mContextRef: WeakReference<Context> = WeakReference(mContext)

    open fun onRestart() {}

    open fun onDestroy() {}
}
