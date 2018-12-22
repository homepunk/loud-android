package homepunk.github.com.vinyl_underground.base

import android.content.Context
import android.databinding.BaseObservable

abstract class BaseViweModel : BaseObservable {
    val mContext: Context?

    constructor(mContext: Context?) : super() {
        this.mContext = mContext
        init()
    }

    open fun init() {}

    open fun onRestart() {}

    open fun onDestroy() {}
}
