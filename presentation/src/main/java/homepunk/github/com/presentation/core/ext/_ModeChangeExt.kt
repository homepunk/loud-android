package homepunk.github.com.presentation.core.ext

import android.animation.AnimatorSet
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import android.animation.ObjectAnimator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.Interpolator
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.feature.widget.animation.ReverseInterpolator
import java.nio.file.AccessMode


fun TextView.getColor(color: Int) = ContextCompat.getColor(context, color)
fun View.getColor(color: Int) = ContextCompat.getColor(context, color)

@BindingAdapter(
        requireAll = true,
        value = [
            "onAppModeChange",
            "color"])
fun TextView.setOnAppModeChange(appModeModel: AppModeModel, color: Int) {
    when (color) {
        getColor(R.color.modeEventsColor) -> {
            getColor(R.color.modeEventsColor).let { accentColor ->
                //                alphaAnimator.setFloatValues(0.5f, 1f)
                val textColorAnimator = ObjectAnimator.ofObject(
                        this, // Object to animating
                        "textColor", // Property to animate
                        ArgbEvaluator(), // Interpolation function
                        getColor(R.color.subtitle3), // Start color
                        accentColor // End color
                )
                textColorAnimator.duration = 800
                val textSizeAnimator = ValueAnimator.ofFloat(32f, 36f)
                textSizeAnimator.addUpdateListener { valueAnimator ->
                    val animatedValue = valueAnimator.animatedValue as Float
                    textSize = animatedValue
                }
                textSizeAnimator.duration = 300
                val set = AnimatorSet()
                textSizeAnimator.interpolator = AccelerateDecelerateInterpolator()
                textColorAnimator.interpolator = AccelerateDecelerateInterpolator()
                set.playTogether(textColorAnimator, textSizeAnimator)
//                textColorAnimator.interpolator = AccelerateInterpolator()
                if (appModeModel.mode == AppMode.EVENTS) {
                    set.start()
                } else {
                    textSizeAnimator.interpolator = ReverseInterpolator(textSizeAnimator.interpolator as Interpolator)
                    textColorAnimator.interpolator = ReverseInterpolator(textColorAnimator.interpolator as Interpolator)
                    set.start()
                }
            }
        }
        getColor(R.color.pale_red2) -> {
            getColor(R.color.pale_red2).let { accentColor ->
                //                alphaAnimator.setFloatValues(0.5f, 1f)
                val textColorAnimator = ObjectAnimator.ofObject(
                        this, // Object to animating
                        "textColor", // Property to animate
                        ArgbEvaluator(), // Interpolation function
                        getColor(R.color.subtitle3), // Start color
                        accentColor // End color
                )
                textColorAnimator.duration = 800
                val textSizeAnimator = ValueAnimator.ofFloat(32f, 36f)
                textSizeAnimator.addUpdateListener { valueAnimator ->
                    val animatedValue = valueAnimator.animatedValue as Float
                    textSize = animatedValue
                }
                textSizeAnimator.duration = 300
                val set = AnimatorSet()
                textSizeAnimator.interpolator = AccelerateDecelerateInterpolator()
                textColorAnimator.interpolator = AccelerateDecelerateInterpolator()
                set.playTogether(textColorAnimator, textSizeAnimator)
//                textColorAnimator.interpolator = AccelerateInterpolator()
                if (appModeModel.mode != AppMode.EVENTS) {
                    set.start()
                } else {
                    textSizeAnimator.interpolator = ReverseInterpolator(textSizeAnimator.interpolator as Interpolator)
                    textColorAnimator.interpolator = ReverseInterpolator(textColorAnimator.interpolator as Interpolator)
                    set.start()
                }
            }

        }
    }
}


var startDelay = 0L
var currentAppModeModel: AppModeModel? = null

@BindingAdapter("onAppModeChange")
fun TextView.setOnAppModeChange(appModeModel: AppModeModel) {
    if (currentAppModeModel != appModeModel) {
        startDelay = 0L
        currentAppModeModel = appModeModel
    } else {
        startDelay += 200L
    }
    val textColorAnimator = ObjectAnimator.ofObject(
            this, // Object to animating
            "textColor", // Property to animate
            ArgbEvaluator(), // Interpolation function
            getColor(if (appModeModel.mode == AppMode.EVENTS) R.color.pale_red2 else R.color.modeEventsColor), // Start color
            getColor(if (appModeModel.mode != AppMode.EVENTS) R.color.pale_red2 else R.color.modeEventsColor) // End color
    ).setDuration(800)
    textColorAnimator.startDelay = startDelay
    textColorAnimator.interpolator = AccelerateDecelerateInterpolator()
    textColorAnimator.start()
}

@BindingAdapter("onAppModeChangeTint")
fun View.onAppModeChangeTint(appModeModel: AppModeModel) {
    if (currentAppModeModel != appModeModel) {
        startDelay = 0L
        currentAppModeModel = appModeModel
    } else {
        startDelay += 200L
    }
    val colorAnim = ObjectAnimator.ofInt(this.backgroundTintList, "backgroundTintList",
            getColor(if (appModeModel.mode == AppMode.EVENTS) R.color.pale_red2 else R.color.modeEventsColor),
            getColor(if (appModeModel.mode != AppMode.EVENTS) R.color.pale_red2 else R.color.modeEventsColor))
    colorAnim.setEvaluator(ArgbEvaluator())
    colorAnim.startDelay = startDelay
    colorAnim.duration = 800
    colorAnim.addUpdateListener { animation ->
        val animatedValue = animation.animatedValue as Int
        backgroundTintList = ColorStateList.valueOf(animatedValue)
    }
    colorAnim.interpolator = AccelerateDecelerateInterpolator()
    colorAnim.start()
}
