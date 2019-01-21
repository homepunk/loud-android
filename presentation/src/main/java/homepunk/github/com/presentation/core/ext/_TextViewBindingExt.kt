package homepunk.github.com.presentation.core.ext

import android.widget.TextView
import androidx.databinding.BindingAdapter

/**Created by Homepunk on 18.01.2019. **/
@BindingAdapter("textRes")
fun TextView.bindTextRes(value: Int) {
    text = context.getString(value)
}