package homepunk.github.com.presentation.util.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val startLeft: Int,
                           private val left: Int,
                           private val right: Int = 0,
                           private val top: Int = 0,
                           private val bottom: Int = 0)
    : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            left = if (parent.getChildAdapterPosition(view) == 0)
                startLeft else
                this@MarginItemDecoration.left

            right = this@MarginItemDecoration.right
            top = this@MarginItemDecoration.top
            bottom = this@MarginItemDecoration.bottom
        }
    }
}