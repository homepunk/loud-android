package homepunk.github.com.presentation.feature.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**Created by Homepunk on 28.01.2019. **/
class OrderChildLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {
    private var line_height: Int = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        assert(View.MeasureSpec.getMode(widthMeasureSpec) != View.MeasureSpec.UNSPECIFIED)

        val width = View.MeasureSpec.getSize(widthMeasureSpec)

        // The next line is WRONG!!! Doesn't take into account requested MeasureSpec mode!
        var height = View.MeasureSpec.getSize(heightMeasureSpec) - paddingTop - paddingBottom
        val count = childCount
        var line_height = 0

        var xpos = paddingLeft
        var ypos = paddingTop

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility !== View.GONE) {
                val lp = child.layoutParams as ViewGroup.LayoutParams
                child.measure(
                        View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST),
                        View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED))

                val childw = child.measuredWidth
                line_height = Math.max(line_height, child.measuredHeight + lp.height)

                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += line_height
                }

                xpos += childw + lp.width
            }
        }
        this.line_height = line_height

        if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.UNSPECIFIED) {
            height = ypos + line_height

        } else if (View.MeasureSpec.getMode(heightMeasureSpec) == View.MeasureSpec.AT_MOST) {
            if (ypos + line_height < height) {
                height = ypos + line_height
            }
        }
        setMeasuredDimension(width, height)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.LayoutParams(1, 1) // default of 1px spacing
    }

    override fun checkLayoutParams(p: ViewGroup.LayoutParams): Boolean {
        return p is ViewGroup.LayoutParams
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val count = childCount
        val width = r - l
        var xpos = paddingLeft
        var ypos = paddingTop

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility !== View.GONE) {
                val childw = child.measuredWidth
                val childh = child.measuredHeight
                val lp = child.layoutParams as ViewGroup.LayoutParams
                if (xpos + childw > width) {
                    xpos = paddingLeft
                    ypos += line_height
                }
                child.layout(xpos, ypos, xpos + childw, ypos + childh)
                xpos += childw + lp.width
            }
        }
    }
}