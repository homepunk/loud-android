package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.databinding.LayoutItemUpcomingEventBinding
import homepunk.github.com.presentation.feature.discover.event.upcoming.EventModel
import homepunk.github.com.presentation.util.DimensionUtil


/**Created by Homepunk on 23.01.2019. **/

@BindingAdapter("children")
fun LinearLayout.bindChildren(children: List<EventModel>) {
    for (i in 0 until children.size) {
        val binding = LayoutItemUpcomingEventBinding.inflate(LayoutInflater.from(context), null, false)
        binding.model = children[i]
        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        if (i != 0) {
            params.topMargin = DimensionUtil.dpToPx(context, 10f)
        }
        addView(binding.root, params)
    }
}

@BindingAdapter("onClick")
fun LinearLayout.bindOnClick(listener: View.OnClickListener?) {
    listener?.run {
        setOnClickListener(this)
    }
}

@BindingAdapter("expand")
fun LinearLayout.expand(isExpanded: Boolean) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

    val va: ValueAnimator?
    if (isExpanded) {
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
            if (isExpanded) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            } else {
                visibility = GONE
            }
        }
    })
    va.duration = 300
    va.start()
}