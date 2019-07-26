package homepunk.github.com.presentation.feature.widget.filterlayout

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayout
import homepunk.github.com.presentation.core.ext.getColor
import homepunk.github.com.presentation.core.ext.onGlobalLayout
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.feature.widget.animation.GammaEvaluator


/**Created by Homepunk on 28.01.2019. **/
class FiltersLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private var currentFilterItem: FilterItem? = null

    private var filterGap: Int
    private var verticalMargin: Int
    private var textAllCaps: Boolean
    private var isFilterFixedWidth: Boolean
    @ColorInt
    private var highlightColor: Int

    private var mFilters = arrayOf<FilterModel>()
    private val mFiltersCount
        get() = mFilters.size

    private var mFiltersLayout: FlexboxLayout
    private var mHighlightView: View

    init {
        val typedArray = context.obtainStyledAttributes(attrs, homepunk.github.com.presentation.R.styleable.FiltersLayout, 0, 0)
        try {
            filterGap = typedArray.getDimensionPixelSize(homepunk.github.com.presentation.R.styleable.FiltersLayout_fl_buttonGap, 0)
            verticalMargin = typedArray.getDimensionPixelSize(homepunk.github.com.presentation.R.styleable.FiltersLayout_fl_tabVerticalMargin, 0)
            highlightColor = typedArray.getColor(homepunk.github.com.presentation.R.styleable.FiltersLayout_fl_highlightedColor, getColor(homepunk.github.com.presentation.R.color.modeEventsColor))
            textAllCaps = typedArray.getBoolean(homepunk.github.com.presentation.R.styleable.FiltersLayout_fl_textAllCaps, true)
            isFilterFixedWidth = typedArray.getBoolean(homepunk.github.com.presentation.R.styleable.FiltersLayout_fl_tabFixedWidth, false)
        } finally {
            typedArray.recycle()
        }

        LayoutInflater.from(context).inflate(homepunk.github.com.presentation.R.layout.layout_filter_menu, this, true)
        clipChildren = false
        clipToPadding = false

        mHighlightView = findViewById(homepunk.github.com.presentation.R.id.highlight_strip)
        mFiltersLayout = findViewById(homepunk.github.com.presentation.R.id.layout_filters)
        mFiltersLayout.flexWrap = FlexWrap.WRAP
        mFiltersLayout.flexDirection = FlexDirection.ROW
    }

    fun setHighlightColor(color: Int) {
        highlightColor = color
        currentFilterItem?.updateHighlightColor(color)
        mHighlightView.backgroundTintList = ColorStateList.valueOf(highlightColor)

        ObjectAnimator.ofObject(mHighlightView, // Object to animating
                "backgroundTintList", // Property to animate
                GammaEvaluator(),// Interpolation function)
                mHighlightView.backgroundTintList.defaultColor,
                color)
                .apply {
                    interpolator = AccelerateDecelerateInterpolator()
                    duration = 500
                    start()
                }

    }

    private fun highlightItem(position: Int) {
        if (currentFilterItem?.index != position) {
            currentFilterItem?.highlight(false)
            currentFilterItem = (mFiltersLayout.getChildAt(position) as FilterItem)
                    .apply {
                        highlightColor = this@FiltersLayout.highlightColor
                        highlight(true)
                        updateHighlightView(measuredWidth, x.toInt(), y.toInt() + measuredHeight - dp2px(1f))
                    }
        }
    }

    fun updateHighlightView(width: Int, toX: Int, toY: Int) {
        (mHighlightView.layoutParams as MarginLayoutParams).let { lP ->
            val translateX = ValueAnimator.ofInt(lP.marginStart, toX).apply {
                addUpdateListener {
                    lP.marginStart = it.animatedValue as Int
                    mHighlightView.layoutParams = lP
                }
            }
            val translateY = ValueAnimator.ofInt(lP.topMargin, toY).apply {
                addUpdateListener { lP.topMargin = it.animatedValue as Int }
            }
            val widthAnimation = ValueAnimator.ofInt(lP.width, width).apply {
                addUpdateListener {
                    lP.width = it.animatedValue as Int
                }
            }
            with(AnimatorSet()) {
                duration = 500
                interpolator = AccelerateDecelerateInterpolator()
                playTogether(translateX, translateY, widthAnimation)
                start()
            }
        }
    }

    fun addFilters(arr: Array<FilterModel>) {
        mFilters = arr
        setUpLayout()
    }

    private fun setUpLayout() {
        var highlightPosition = -1
        for (i in 0 until mFiltersCount) {
            val filter = mFilters[i].also {
                if (it.isSelected) {
                    highlightPosition = i
                }
            }
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            params.marginEnd = filterGap
            params.topMargin = verticalMargin
            params.bottomMargin = verticalMargin
            mFiltersLayout.addView(getFilterItem(filter, i).apply {
                setOnClickListener {
                    highlightItem(i)
                    filter.isSelected.swap()
                }
            }, params)
        }
        mHighlightView.backgroundTintList = ColorStateList.valueOf(highlightColor)
        onGlobalLayout { highlightItem(highlightPosition) }
    }

    private fun getFilterItem(item: FilterModel, position: Int): FilterItem {
        val tab = FilterItem(context)
        tab.setUpFilter(position, item.name, textAllCaps)
        tab.highlightColor = highlightColor

        return tab
    }

    private fun dp2px(dpValue: Float) = (dpValue * resources.displayMetrics.density + 0.5f).toInt()
}
