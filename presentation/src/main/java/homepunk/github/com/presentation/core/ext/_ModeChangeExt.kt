package homepunk.github.com.presentation.core.ext

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.feature.widget.animation.GammaEvaluator
import timber.log.Timber




fun TextView.getColor(color: Int) = ContextCompat.getColor(context, color)
fun View.getColor(color: Int) = ContextCompat.getColor(context, color)

@BindingAdapter(value = ["updateModeTitleOnModeChange", "btnMode"])
fun TextView.updateModeTitleOnModeChange(prevAppModeModel: AppModeModel?, prevBtnMode: AppMode?,
                                         appModeModel: AppModeModel?, btnMode: AppMode?) {
    if (appModeModel != prevAppModeModel) {
        val inactiveColor = getColor(homepunk.github.com.presentation.R.color.subtitle3)
        // IF FIRST RUN
        if (prevAppModeModel == null && appModeModel != null) {
            if (btnMode == appModeModel.mode) {
                setTextColor(appModeModel.color)
                textSize = 36f
            } else {
                setTextColor(inactiveColor)
                textSize = 34f
            }
            return
        }

        val textColorAnimator = ObjectAnimator.ofObject(
                this, // Object to animating
                "textColor", // Property to animate
                GammaEvaluator(), // Interpolation function
                if (currentTextColor == inactiveColor) inactiveColor else prevAppModeModel!!.color,
                if (currentTextColor == inactiveColor) appModeModel!!.color else inactiveColor
        ).setDuration(duration)
        val textSizeAnimator = ObjectAnimator.ofFloat(
                this, // Object to animating
                "textSize", // Property to animate
                if (currentTextColor == inactiveColor) 34F else 36F,
                if (currentTextColor == inactiveColor) 36F else 34F
        ).setDuration(300)
        textSizeAnimator.interpolator = LinearInterpolator()
        AnimatorSet().apply {
            playTogether(textColorAnimator, textSizeAnimator)
            start()
        }
    }
}


var duration = 600L
var startDelay = 0L
var currentAppModeModel: AppModeModel? = null

@BindingAdapter("updateTextColorOnModeChange")
fun TextView.updateTextColorOnModeChange(prevAppModeModel: AppModeModel?, appModeModel: AppModeModel) {
    Timber.w("updateTextColorOnModeChange prevAppModeModel IS NULL = ${prevAppModeModel == null}, appModeModel IS NULL = ${appModeModel == null}")
  /*  if (currentAppModeModel != appModeModel) {
        startDelay = 0L
        currentAppModeModel = appModeModel
    } else {
        startDelay += 100L
    }*/
    if (prevAppModeModel == null) {
        setTextColor(appModeModel.color)
    } else {
        val textColorAnimator = ObjectAnimator.ofObject(
                this, // Object to animating
                "textColor", // Property to animate
                GammaEvaluator(), // Interpolation function
                getColor(if (appModeModel.mode == AppMode.EVENTS) homepunk.github.com.presentation.R.color.pale_red2 else homepunk.github.com.presentation.R.color.modeEventsColor), // Start color
                getColor(if (appModeModel.mode != AppMode.EVENTS) homepunk.github.com.presentation.R.color.pale_red2 else homepunk.github.com.presentation.R.color.modeEventsColor) // End color
        ).setDuration(duration)
        textColorAnimator.startDelay = startDelay
        textColorAnimator.interpolator = AccelerateDecelerateInterpolator()
        textColorAnimator.start()
    }
}


@BindingAdapter("updateTintOnModeChange")
fun View.updateTintOnModeChange(prevAppModeModel: AppModeModel?, appModeModel: AppModeModel) {
   /* if (currentAppModeModel != appModeModel) {
        startDelay = 0L
        currentAppModeModel = appModeModel
    } else {
        startDelay += 100L
    }*/
    if (prevAppModeModel == null) {
        backgroundTintList = ColorStateList.valueOf(appModeModel.color)
    } else {
        val colorAnim = ObjectAnimator.ofInt(this.backgroundTintList, "backgroundTintList",
                getColor(if (appModeModel.mode == AppMode.EVENTS) homepunk.github.com.presentation.R.color.pale_red2 else homepunk.github.com.presentation.R.color.modeEventsColor),
                getColor(if (appModeModel.mode != AppMode.EVENTS) homepunk.github.com.presentation.R.color.pale_red2 else homepunk.github.com.presentation.R.color.modeEventsColor))
        colorAnim.setEvaluator(GammaEvaluator())
        colorAnim.startDelay = startDelay
        colorAnim.duration = duration
        colorAnim.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            backgroundTintList = ColorStateList.valueOf(animatedValue)
        }
        colorAnim.interpolator = AccelerateDecelerateInterpolator()
        colorAnim.start()
    }
}
