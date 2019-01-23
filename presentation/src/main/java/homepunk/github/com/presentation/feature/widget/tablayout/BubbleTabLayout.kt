package homepunk.github.com.presentation.feature.widget.tablayout

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.R


/**Created by Homepunk on 22.01.2019. **/
class BubbleTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : HorizontalScrollView(context, attrs, defStyleAttr) {

    private var buttonGap: Int
    private var buttonTextAllCaps: Boolean
    @ColorInt
    private var buttonDefaultColor: Int
    @ColorInt
    private var buttonHighlightColor: Int

    private var mTabLayout: LinearLayout
    private var mPager: ViewPager? = null

    private var mTabTextArray = arrayOf<String>()
    private val mTabCount
        get() = mTabTextArray.size

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BubbleTabLayout, 0, 0)
        try {
            buttonGap = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_buttonGap, 0)
            buttonDefaultColor = typedArray.getColor(R.styleable.BubbleTabLayout_bubble_defaultColor, Color.DKGRAY)
            buttonHighlightColor = typedArray.getColor(R.styleable.BubbleTabLayout_bubble_highlightedColor, Color.LTGRAY)
            buttonTextAllCaps = typedArray.getBoolean(R.styleable.BubbleTabLayout_bubble_textAllCaps, true)
        } finally {
            typedArray.recycle()
        }

        isFillViewport = true
        clipChildren = false
        clipToPadding = false
        mTabLayout = LinearLayout(context)
        addView(mTabLayout)
    }


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
                        if (isSelected) {
                            updateBackgroundWithoutBorder(buttonHighlightColor)
                        } else {
                            updateBackgroundWithoutBorder(buttonDefaultColor)
                        }
                    }
        }
    }

    fun setTitleArray(arr: Array<String>) {
        mTabTextArray = arr
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        for (i in 0 until mTabTextArray.size) {
            val tab = BubbleTab(context)
            val title = mTabTextArray[i]
            tab.tabText.isAllCaps = buttonTextAllCaps
            tab.tabText.text = title
            tab.updateBackgroundWithoutBorder(buttonDefaultColor)
            val params = LinearLayout.LayoutParams(0,
                    ViewGroup.LayoutParams.MATCH_PARENT, 1f)

            if (i == mTabTextArray.size - 1) {
                params.rightMargin = 0
            } else {
                params.rightMargin = buttonGap
            }
            mTabLayout.addView(tab, params)
        }
    }

    private fun dp2px(dpValue: Float) = (dpValue * resources.displayMetrics.density + 0.5f).toInt()
}