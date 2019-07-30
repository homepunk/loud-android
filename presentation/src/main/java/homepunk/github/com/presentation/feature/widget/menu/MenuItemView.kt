package homepunk.github.com.presentation.feature.widget.menu

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.ext.getColor
import homepunk.github.com.presentation.core.ext.getScaleAnimation
import homepunk.github.com.presentation.core.ext.getSizeAnimation
import kotlin.math.roundToInt

class MenuItemView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var menuItem: MenuItem
    private var menuItemView: TextView?
    private var scaleFactor = 1.2f

    constructor(context: Context, menuItem: MenuItem) : this(context) {
        this.menuItem = menuItem
    }

    init {
        LayoutInflater.from(context)!!.inflate(R.layout.custom_layout_item_menu, this, true)
        menuItemView = findViewById<TextView>(R.id.item)
        with(menuItem) {
            menuItemView?.setText(nameResId)
            menuItemView?.setTextColor(getColor(if (isCurrent) highlightColorResId else defaultColorResId))
        }
    }

    fun highlight(highligh: Boolean) {
        pivotX = 0f
        pivotY = height.toFloat()

        val scaleAnimation = getScaleAnimation(if (highligh) scaleFactor else 1f, if (highligh) 1f else scaleFactor)
        val sizeAnimation = getSizeAnimation((if (highligh) height * scaleFactor else height / scaleFactor).roundToInt())
        AnimatorSet().apply {
            playTogether(scaleAnimation, sizeAnimation)
            start()
        }
    }

    fun View.getSizeAnimation(newSize: Int): Animator {
        val sizeAnimator = ValueAnimator.ofInt(height, newSize)
        sizeAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            with(layoutParams) {
                height = animatedValue
                width = animatedValue
            }
            requestLayout()
        }
        sizeAnimator.duration = 400
        return sizeAnimator
    }

    fun View.getScaleAnimation(oldScale: Float, newScale: Float): ValueAnimator {
        val scaleAnimator = ValueAnimator.ofFloat(oldScale, newScale)
        scaleAnimator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            scaleX = animatedValue
            scaleY = animatedValue
        }
        scaleAnimator.duration = 400
        return scaleAnimator
    }

    enum class MenuItem(var isCurrent: Boolean, var nameResId: Int, var highlightColorResId: Int, var defaultColorResId: Int, var itemType: Int)
}