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
import homepunk.github.com.presentation.core.ext.getMaxLenString


/**Created by Homepunk on 22.01.2019. **/
class BubbleTabLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : HorizontalScrollView(context, attrs, defStyleAttr) {

    private var startMargin: Int
    private var buttonGap: Int
    private var buttonVerticalMargin: Int
    private var buttonTextAllCaps: Boolean
    private var buttonFixedWidth: Boolean
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
            buttonVerticalMargin = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_tabVerticalMargin, 0)
            startMargin = typedArray.getDimensionPixelSize(R.styleable.BubbleTabLayout_bubble_startMargin, 0)
            buttonDefaultColor = typedArray.getColor(R.styleable.BubbleTabLayout_bubble_defaultColor, Color.DKGRAY)
            buttonHighlightColor = typedArray.getColor(R.styleable.BubbleTabLayout_bubble_highlightedColor, Color.LTGRAY)
            buttonTextAllCaps = typedArray.getBoolean(R.styleable.BubbleTabLayout_bubble_textAllCaps, true)
            buttonFixedWidth = typedArray.getBoolean(R.styleable.BubbleTabLayout_bubble_tabFixedWidth, true)
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
                            updateBackground(R.drawable.filter_backgorund_selected)
                        } else {
                            updateBackground(R.drawable.filter_background)
                        }
                    }
        }
    }

    fun setTitleArray(arr: Array<String>) {
        mTabTextArray = arr
        setUpTabLayout()
    }

    private fun setUpTabLayout() {
        val tabWidth = if (buttonFixedWidth) {
            getBubbleTab(mTabTextArray.getMaxLenString())
                    .apply { measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT) }
                    .measuredWidth
        } else {
            ViewGroup.LayoutParams.WRAP_CONTENT
        }

        for (i in 0 until mTabTextArray.size) {
            val tab = getBubbleTab(mTabTextArray[i]).apply {
                setOnClickListener { highlightTab(i) }
            }
            val params = LinearLayout.LayoutParams(tabWidth, ViewGroup.LayoutParams.MATCH_PARENT)

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

    private fun getBubbleTab(title: String): BubbleTab {
        val tab = BubbleTab(context)
        tab.tabText.isAllCaps = buttonTextAllCaps
        tab.tabText.text = title

        return tab
    }

    private fun dp2px(dpValue: Float) = (dpValue * resources.displayMetrics.density + 0.5f).toInt()
}