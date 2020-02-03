package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.util.DimensionUtil
import timber.log.Timber


/**Created by Homepunk on 30.05.2019. **/


private var xDelta: Float = 0f
private var yDelta: Float = 0f

@BindingAdapter("moveOnTouch")
fun ViewGroup.moveOnTouch(moveOnTouch: Boolean) {
    if (moveOnTouch) {
        setOnTouchListener { _, event ->
            val x = event.rawX
            val y = event.rawY



            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> {
                    val lParams = layoutParams as RelativeLayout.LayoutParams
                    xDelta = x - this@moveOnTouch.x
                    yDelta = y - this@moveOnTouch.y
                    Timber.w("MotionEvent.ACTION_DOWN = x = $x, y = $y, lParams.leftMargin = ${lParams.leftMargin}, lParams.topMargin = ${lParams.topMargin}, this@moveOnTouch.x = ${this@moveOnTouch.x}, this@moveOnTouch.y = ${this@moveOnTouch.y}")
                    layoutParams = lParams.apply {
                        removeRule(RelativeLayout.ALIGN_PARENT_START)
                        removeRule(RelativeLayout.ALIGN_PARENT_END)
                        removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                        removeRule(RelativeLayout.ALIGN_PARENT_TOP)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    val lParams = layoutParams as RelativeLayout.LayoutParams
                    layoutParams = lParams
                            .apply {
                                val screenCenter = DimensionUtil.screenWidth / 2
                                val viewHalfWidth = this@moveOnTouch.width / 2

                                val margin = resources.getDimension(R.dimen.margin_big_20dp).toInt()
                                if ((x - xDelta) + viewHalfWidth > screenCenter) {
                                    Timber.w("MotionEvent.ACTION_UP = x = $x, y = $y, ALIGN_PARENT_END")
                                    addRule(RelativeLayout.ALIGN_PARENT_END, 1)
                                    addRule(RelativeLayout.ALIGN_PARENT_START, 0)
                                } else {
                                    Timber.w("MotionEvent.ACTION_UP = x = $x, y = $y, ALIGN_PARENT_START")
                                    addRule(RelativeLayout.ALIGN_PARENT_END, 0)
                                    addRule(RelativeLayout.ALIGN_PARENT_START, 1)
                                }
                                marginStart = margin
                                marginEnd = margin
                            }
                }
                MotionEvent.ACTION_MOVE -> {
                    val viewX = (x - xDelta).toInt()
                    val viewY = (y - yDelta).toInt()
//                    if (viewX > 0 && viewX + this@moveOnTouch.width < DimensionUtil.screenWidth
//                            && viewY > 0 && viewY + this@moveOnTouch.height < DimensionUtil.scerenHeight) {
                    val layoutParams = layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.leftMargin = viewX
                    layoutParams.topMargin = viewY
                    layoutParams.rightMargin = 0
                    layoutParams.bottomMargin = 0
                    this.layoutParams = layoutParams
//                    }
                }
            }
            invalidate()
            true
        }
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
            visibility = View.VISIBLE
        } else {
            layoutParams.height = 0
            visibility = View.GONE
        }
        return
    }

    val va: ValueAnimator?
    if (expand) {
        va = ValueAnimator.ofInt(1, measuredHeight)
        va.interpolator = AccelerateInterpolator()
        visibility = View.VISIBLE
    } else {
        va = ValueAnimator.ofInt(measuredHeight, 0)
        va.interpolator = DecelerateInterpolator()
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
                visibility = View.GONE
            }
        }
    })
    va.duration = 400
    va.start()
}

@BindingAdapter(
        value = ["expand"],
        requireAll = false)
fun ViewGroup.expand(prevExpand: Boolean,
                        expand: Boolean) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    if (expand == prevExpand) {
        if (expand) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            visibility = View.VISIBLE
        } else {
            layoutParams.height = 0
            visibility = View.GONE
        }
        return
    }

    val va: ValueAnimator?
    if (expand) {
        va = ValueAnimator.ofInt(1, measuredHeight)
        va.interpolator = AccelerateInterpolator()
        visibility = View.VISIBLE
    } else {
        va = ValueAnimator.ofInt(measuredHeight, 0)
        va.interpolator = DecelerateInterpolator()
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
                visibility = View.GONE
            }
        }
    })
    va.duration = 400
    va.start()
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

        val maxRadius = resources.getDimension(homepunk.github.com.presentation.R.dimen.radius_default_card)
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