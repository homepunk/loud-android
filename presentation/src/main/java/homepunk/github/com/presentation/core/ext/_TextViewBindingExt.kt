package homepunk.github.com.presentation.core.ext

import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.core.wrapper.AnimationListenerWrapper

/**Created by Homepunk on 18.01.2019. **/
@BindingAdapter("textRes")
fun TextView.bindTextRes(value: Int) {
    text = context.getString(value)
}
