package homepunk.github.com.presentation.base

import android.arch.lifecycle.ViewModel
import android.content.Context
import android.databinding.BaseObservable

abstract class BaseViweModel(val mContext: Context?) : BaseObservable() {

    open fun onRestart() {}

    open fun onDestroy() {}
}
