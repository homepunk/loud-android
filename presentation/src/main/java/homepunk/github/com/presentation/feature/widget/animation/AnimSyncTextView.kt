package homepunk.github.com.presentation.feature.widget.animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.ext.onGlobalLayout
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import timber.log.Timber

/**Created by Homepunk on 19.08.2019. **/
class AnimSyncTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {
    //    var currentAnimationSet: AnimatorSet? = null
    var isAnimationRunning = false
    var defaultWidth: Int = 0
    var scaledWidth: Int = 0

    init {
//        TypefaceCompat.createFromResourcesFontFile(context, resources, R.font.lato_bold, NORMAL)
        typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        onGlobalLayout {
            pivotX = 0f
            pivotY = height.toFloat()
        }

    }

    fun scaleWithWidthAdjustment(scale: Float) {
        if (defaultWidth == 0) {
            defaultWidth = measuredWidth
            scaledWidth = Math.round(measuredWidth * scale)
        }
        onGlobalLayout {
            scaleX = scale
            scaleY = scale
            layoutParams.width = scaledWidth
            requestLayout()
        }
    }

    fun scale(scale: Float) {
        scaleX = scale
        scaleY = scale
    }


    override fun setText(text: CharSequence?, type: BufferType?) {
//        Timber.w("CURRENT TEXT ${getText()}, current width = ${measuredWidth} new text = $text, new width = ${TextPaint().measureText(text.toString())} ")
        defaultWidth = 0
        scaledWidth = 0
        super.setText(text, type)
//        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }



    fun animateScaleChange(oldScale: Float, scale: Float) {
        Timber.w("animateScaleChange CURRENT TEXT ${getText()}, current width = ${measuredWidth} ")

        if (defaultWidth == 0) {
            defaultWidth = measuredWidth
            scaledWidth = Math.round(measuredWidth * scale)
        }

        val desiredWidth = if (scale > oldScale) scaledWidth else defaultWidth
        Timber.w("animateScaleChange CURRENT TEXT ${getText()}: measuredWidth = $measuredWidth, after animateScaleChange = $desiredWidth")
        val scaleAnimator = ValueAnimator.ofFloat(oldScale, scale)
        scaleAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            scaleX = animatedValue
            scaleY = animatedValue
        }
        val widthAnimator = ValueAnimator.ofInt(measuredWidth, desiredWidth)
        widthAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            layoutParams.width = animatedValue
            requestLayout()
        }
        AnimatorSet().let { set ->
            set.playTogether(widthAnimator, scaleAnimator)
            set.duration = 300
            set.setTarget(this)
            set.addListener(object : AnimatorListenerWrapper() {
                override fun onAnimationStart(animation: Animator?, isReverse: Boolean) {
                    isAnimationRunning = true
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Timber.w("SCALE: onAnimationEnd measuredWidth = $measuredWidth, desiredWidth = $desiredWidth")
                    isAnimationRunning = false
                    layoutParams.width = if (layoutParams.width > desiredWidth) layoutParams.width else desiredWidth
                    requestLayout()
                }
            })
            set.start()
        }
    }
}