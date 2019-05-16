package homepunk.github.com.presentation.feature.widget.timeline

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import java.text.SimpleDateFormat
import java.util.*

/**Created by Homepunk on 14.05.2019. **/
abstract class TimelineAdapter<T> {
    var itemList: ArrayList<T>? = null

    abstract fun getDate(value: T) : String

    abstract fun getDateFormat(): SimpleDateFormat

    abstract fun onInflateLayout(context: Context, root: ViewGroup): ViewDataBinding

    abstract fun onBindLayout(binding: ViewDataBinding, index: Int)
}