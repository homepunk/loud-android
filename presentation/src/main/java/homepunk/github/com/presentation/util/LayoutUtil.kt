package homepunk.github.com.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**Created by Homepunk on 13.05.2019. **/
object LayoutUtil {
    fun <T : ViewDataBinding> inflateBindingLayout(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!
}