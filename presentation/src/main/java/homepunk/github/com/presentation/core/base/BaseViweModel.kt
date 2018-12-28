package homepunk.github.com.presentation.core.base

import android.content.Context
import androidx.lifecycle.ViewModel
import java.lang.ref.WeakReference

abstract class BaseViweModel(mContext: Context) : ViewModel() {
    val mContextRef: WeakReference<Context> = WeakReference(mContext)
}
