package homepunk.github.com.presentation.core.ext

import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import timber.log.Timber

/**Created by Homepunk on 28.03.2019. **/

@BindingAdapter("interceptChildTouchEvent")
fun FrameLayout.interceptChildTouchEvent(intercept: Boolean) {
    setOnClickListener { Timber.w("PARENT CLICKED") }
    getChildAt(0)?.let {
        it.setOnClickListener { Timber.w("CHILD TOUCHED") }
    }
}