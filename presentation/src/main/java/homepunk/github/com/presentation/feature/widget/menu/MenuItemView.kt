package homepunk.github.com.presentation.feature.widget.menu

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.ext.getScaleAnimation
import homepunk.github.com.presentation.core.ext.getWidthAnimation
import homepunk.github.com.presentation.core.ext.doOnGlobalLayout
import homepunk.github.com.presentation.feature.widget.animation.GammaEvaluator
import kotlin.math.roundToInt

class MenuItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : TextView(context, attrs, defStyleAttr) {

    private lateinit var configuration: MenuItemConfiguration

    constructor(context: Context, text: String, menuItemConfiguration: MenuItemConfiguration) : this(context) {
        with(menuItemConfiguration) {
            configuration = this

            isAllCaps = true
            gravity = Gravity.BOTTOM
            typeface = ResourcesCompat.getFont(context, R.font.lato_bold)

            setText(text)
            setTextColor(textDefaultColor)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizePx)
            doOnGlobalLayout {
                val height = height * scaleFactor
                pivotX = 0f
                pivotY = height
                layoutParams?.height = height.roundToInt()
                requestLayout()
            }
        }
    }

    fun highlight(highlight: Boolean) {
        with(configuration) {
            val scaleAnimation = getScaleAnimation(if (highlight) 1f else scaleFactor, if (highlight) scaleFactor else 1f)
            val widthAnimation = getWidthAnimation((if (highlight) width * scaleFactor else width / scaleFactor).roundToInt())
            val colorAnimator = if (highlight)
                getColorAnimator(textDefaultColor, textHighlightColor) else
                getColorAnimator(textHighlightColor, textDefaultColor)

            AnimatorSet().apply {
                playTogether(scaleAnimation, widthAnimation, colorAnimator)
                duration = 400
                start()
            }
        }
    }

    fun getColorAnimator(fromColor: Int, toCoclor: Int) = ObjectAnimator.ofObject(
            this, // Object to animating
            "textColor", // Property to animate
            GammaEvaluator(), // Interpolation function
            fromColor,
            toCoclor
    )


    class MenuItemConfiguration {
        var scaleFactor: Float = 1f
        var textGap: Int = 0
        var textSizePx: Float = 0f
        var textDefaultColor: Int = 0
        var textHighlightColor: Int = 0
    }
}