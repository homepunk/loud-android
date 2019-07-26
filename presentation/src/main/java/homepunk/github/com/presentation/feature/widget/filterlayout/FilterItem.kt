package homepunk.github.com.presentation.feature.widget.filterlayout

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.R.layout
import homepunk.github.com.presentation.core.ext.getColor
import homepunk.github.com.presentation.feature.widget.animation.GammaEvaluator


/**Created by Homepunk on 22.01.2019. **/
class FilterItem(context: Context) : FrameLayout(context) {
    var highlightColor: Int = Color.RED
    var index: Int = -1
    var defaultTitle: TextView
    var highlightedTitle: TextView

    init {
        val tabLayout = LayoutInflater.from(context).inflate(layout.item_filter, this, true)
        defaultTitle = tabLayout.findViewById(R.id.title)
        highlightedTitle = tabLayout.findViewById(R.id.highlighted_title)
    }

    fun setUpFilter(index: Int, title: String, isTextAllCaps: Boolean) {
        this.index = index
        highlightedTitle.isAllCaps = isTextAllCaps
        highlightedTitle.text = title
        defaultTitle.text = title
        defaultTitle.isAllCaps = isTextAllCaps
    }

    fun updateHighlightColor(color: Int) {
        ObjectAnimator.ofObject(defaultTitle, // Object to animating
                "textColor", // Property to animate
                GammaEvaluator(),// Interpolation function)
                defaultTitle.currentTextColor,
                color)
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 500
                    start()
                }

    }

    fun highlight(highlight: Boolean) {
        ObjectAnimator.ofObject(defaultTitle, // Object to animating
                "textColor", // Property to animate
                GammaEvaluator(),// Interpolation function)
                defaultTitle.currentTextColor,
                if (highlight) highlightColor else getColor(R.color.subtitle3))
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 500
                    start()
                }

    }

}