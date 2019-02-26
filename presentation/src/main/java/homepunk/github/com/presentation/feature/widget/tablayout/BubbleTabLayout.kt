package homepunk.github.com.presentation.feature.widget.tablayout

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.R


/**Created by Homepunk on 22.01.2019. **/
class BubbleTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : HorizontalScrollView(context, attrs, defStyleAttr) {

    private var startMargin: Int
    private var buttonGap: Int
    private var buttonVerticalMargin: Int
    private var buttonTextAllCaps: Boolean
    private var isButtonFixedWidth: Boolean

    private var buttonIconWidth: Int
    private var buttonIconHeight: Int

    @ColorInt
    private var buttonDefaultColor: Int
    @ColorInt
    private var buttonHighlightColor: Int

    private var mTabLayout: LinearLayout
    private var mPager: ViewPager? = null

    internal var mTabItemArray = arrayOf<TabItem?>()
    private val mTabCount
        get() = mTabItemArray.size

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleTabLayout, 0, 0)
        try {
            buttonGap = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_buttonGap, 0)
            buttonIconWidth = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_iconWidth, dp2px(24f))
            buttonIconHeight = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_iconHeight, dp2px(24f))
            buttonVerticalMargin = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_tabVerticalMargin, 0)
            startMargin = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_startMargin, 0)
            buttonDefaultColor = typedArray.getColor(R.styleable.BubbleTabLayout_bubble_defaultColor, Color.DKGRAY)
            buttonHighlightColor = typedArray.getColor(R.styleable.BubbleTabLayout_bubble_highlightedColor, Color.LTGRAY)
            buttonTextAllCaps = typedArray.getBoolean(R.styleable.BubbleTabLayout_bubble_textAllCaps, true)
            isButtonFixedWidth = typedArray.getBoolean(R.styleable.BubbleTabLayout_bubble_tabFixedWidth, true)
        } finally {
            typedArray.recycle()
        }

        isFillViewport = true
        clipChildren = false
        clipToPadding = false
        mTabLayout = LinearLayout(context)
        addView(mTabLayout)
    }

    private var currentTabItem: TabItem? = null
    var currentTabPosition: Int = 0
        set(value) {
            field = value
            currentTabItem = mTabItemArray[currentTabPosition]
            highlightTab(value)
        }
    var onMenuTabClickListener: OnMenuTabClickListener? = null

    fun setupWithViewPager(viewPager: ViewPager?) {
        mPager = viewPager
        mPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                highlightTab(position)
            }
        })
        highlightTab(mPager?.currentItem)
    }

    private fun highlightTab(position: Int?) {
        for (i in 0 until mTabCount) {
            val isSelected = i == position
            mTabLayout.getChildAt(i)
                    ?.run {
                        this as? BubbleTab
                    }?.apply {
                        mTabItemArray[i]!!.isSelected = isSelected
                        if (isSelected) {
                            setTint(ContextCompat.getColor(context, mTabItemArray[i]!!.colorResId))
                        } else {
                            setTint(buttonDefaultColor)
                        }
                    }
        }
    }

    fun setImageAndTitleArray(arr: Array<TabItem?>) {
        mTabItemArray = arr
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        mTabLayout.gravity = Gravity.CENTER_HORIZONTAL
        for (i in 0 until mTabItemArray.size) {
            val tabItem = mTabItemArray[i]!!
            val tab = getBubbleTab(tabItem)
                    .apply {
                        setOnClickListener {
                            currentTabPosition = i
                            onMenuTabClickListener?.onTabClick(i, tabItem)
                            highlightTab(i)
                        }
                    }
            if (tabItem.isSelected) {
                highlightTab(i)
            }
            val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            if (i == 0) {
                params.marginStart = startMargin
            } else {
                params.marginStart = buttonGap
            }

            params.topMargin = buttonVerticalMargin
            params.bottomMargin = buttonVerticalMargin
            mTabLayout.addView(tab, params)
        }
    }

    private fun getBubbleTab(tabItem: TabItem): BubbleTab {
        val tab = BubbleTab(context)
        tab.title.isAllCaps = buttonTextAllCaps
        tab.title.text = context.getString(tabItem.titleResId)
        tab.image.setImageResource(tabItem.iconResId)
        return tab
    }

    private fun dp2px(dpValue: Float) = (dpValue * resources.displayMetrics.density + 0.5f).toInt()

    data class TabItem(@DrawableRes val iconResId: Int,
                       @StringRes val titleResId: Int,
                       @ColorRes val colorResId: Int,
                       var isSelected: Boolean = false)

    open interface OnMenuTabClickListener {
        fun onTabClick(index: Int, item: TabItem)
    }
}