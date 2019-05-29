package homepunk.github.com.presentation.util.layout

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

/**Created by Homepunk on 28.05.2019. **/
class CustomLinearLayoutManager(context: Context, orientation: Int, reverseLayout: Boolean)
    : LinearLayoutManager(context, orientation, reverseLayout) {
    var isVerticalScrollEnabled = true
    var isHorizontalScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return isVerticalScrollEnabled && super.canScrollVertically()
    }

    override fun canScrollHorizontally(): Boolean {
        return isHorizontalScrollEnabled
    }
}