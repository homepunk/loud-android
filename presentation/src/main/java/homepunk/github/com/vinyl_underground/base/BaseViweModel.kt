package homepunk.github.com.vinyl_underground.base

import android.content.Context
import android.databinding.BaseObservable

abstract class BaseViweModel (val mContext: Context?) : BaseObservable() {
    open fun onRestart() {}

    open fun onDestroy() {}
}
