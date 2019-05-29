package homepunk.github.com.presentation.core.ext

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.feature.widget.expandablelayout.ExpandableHeader
import homepunk.github.com.presentation.feature.widget.timeline.TimelineAdapter
import homepunk.github.com.presentation.feature.widget.timeline.TimelineView
import homepunk.github.com.presentation.util.DimensionUtil
import timber.log.Timber

/**Created by Homepunk on 15.05.2019. **/

@BindingAdapter("timeline_adapter")
fun <T> TimelineView<T>.bindAdapter(adapter: TimelineAdapter<T>) {
    this.adapter = adapter
}

@BindingAdapter("headerHint")
fun ExpandableHeader.bindAdapter(hint: String?) {
//    setHint(hint)
}

@BindingAdapter("resizeToFullWidth")
fun ConstraintLayout.resizeToFullWidth(resize: Boolean) {
    val ratio = measuredWidth.toDouble() / measuredHeight
    Timber.w("current [width = $measuredWidth, height = $measuredHeight, ratio = $ratio]")
//    val margin = DimensionUtil.dpToPx<Int>(context, 20f)
    val width = DimensionUtil.screenWidth
    val height = width / ratio
    val card = getChildAt(0) as CardView

    with(layoutParams as ViewGroup.MarginLayoutParams) {
        val animHeight: ValueAnimator
        val animWidth: ValueAnimator
        val animRadius: ValueAnimator

        val maxRadius = resources.getDimension(R.dimen.radius_default_card)
        if (resize) {
            animHeight = ValueAnimator.ofInt(minimumHeight, height.toInt())
            animWidth = ValueAnimator.ofInt(minimumWidth, width)
            animRadius = ValueAnimator.ofFloat(maxRadius, 0f)
        } else {
            animHeight = ValueAnimator.ofInt(measuredHeight, minimumHeight)
            animWidth = ValueAnimator.ofInt(measuredWidth, minimumWidth)
            animRadius = ValueAnimator.ofFloat(0f, maxRadius)
        }

        animHeight.addUpdateListener {
            this.height = it.animatedValue as Int
        }
        animWidth.addUpdateListener {
            this.width = it.animatedValue as Int
            requestLayout()
        }
        animRadius.addUpdateListener {
            card.radius = it.animatedValue as Float
        }
        animRadius.duration = 100
        animRadius.start()

        with(AnimatorSet()) {
            startDelay = 50
            duration = 300
            interpolator = AccelerateInterpolator()
            playTogether(animHeight, animWidth)
            start()
        }
    }
    if (getHeight() != 0) {
        Timber.w("new [width = ${getWidth()}, height = ${getHeight()}, ratio = ${getWidth() / getHeight()}]")
    }
}