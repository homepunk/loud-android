package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper


/**Created by Homepunk on 23.01.2019. **/

@BindingAdapter("onClick")
fun LinearLayout.bindOnClick(listener: View.OnClickListener?) {
    listener?.run {
        setOnClickListener(this)
    }
}

@BindingAdapter(
        value = ["expand"],
        requireAll = false)
fun LinearLayout.expand(prevExpand: Boolean,
                        expand: Boolean) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    if (expand == prevExpand) {
        if (expand) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            visibility = VISIBLE
        } else {
            layoutParams.height = 0
            visibility = GONE
        }
        return
    }

    val va: ValueAnimator?
    if (expand) {
        va = ValueAnimator.ofInt(1, measuredHeight)
        visibility = VISIBLE
    } else {
        va = ValueAnimator.ofInt(measuredHeight, 0)
    }

    va.addUpdateListener { animation ->
        layoutParams.height = animation.animatedValue as Int
        requestLayout()
    }
    va.addListener(object : AnimatorListenerWrapper() {
        override fun onAnimationEnd(animation: Animator?) {
            if (expand) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                visibility = GONE
            }
        }
    })
    va.duration = 300
    va.start()
}
