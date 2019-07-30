package homepunk.github.com.presentation.feature.widget.menu

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class MenuLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    var mMenuItems: List<MenuItemView.MenuItem>? = null
    var mCurrentMenuItemIndex = 0

    init {
        orientation = HORIZONTAL
    }

    fun setMenu(menuItems: List<MenuItemView.MenuItem>) {
        mMenuItems = menuItems
        setUpMenu()
    }

    private fun setUpMenu() {
        if (childCount != 0) removeAllViews()

        mMenuItems!!.forEachIndexed { index, item ->
            addView(MenuItemView(context, item))
        }
    }
}