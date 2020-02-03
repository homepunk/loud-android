package homepunk.github.com.presentation.feature.widget.menu

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.databinding.ObservableField
import homepunk.github.com.presentation.R.*
import homepunk.github.com.presentation.core.ext.doOnGlobalLayout

class MenuLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    var mMenuItems: List<MenuItem>? = null
    var mMenuItemViews: MutableList<MenuItemView> = arrayListOf()
    var mMenuItemConfiguration = ObservableField<MenuItemView.MenuItemConfiguration>()

    var mCurrentMenuItemIndex = -1
    var mCurrentActionId = -1

    init {
        orientation = HORIZONTAL
        mMenuItemConfiguration.set(MenuItemView.MenuItemConfiguration()
                .apply {
                    val typedArray = context.obtainStyledAttributes(attrs, styleable.MenuLayout, 0, 0)
                    try {
                        mCurrentActionId = typedArray.getInt(styleable.MenuLayout_menu_currentActionId, 0)

                        scaleFactor = typedArray.getFloat(styleable.MenuLayout_menu_scaleFactor, 1f)
                        textGap = typedArray.getDimensionPixelSize(styleable.MenuLayout_menu_textGap, 0)
                        textSizePx = typedArray.getDimensionPixelSize(styleable.MenuLayout_menu_textSize, 0).toFloat()
                        textDefaultColor = typedArray.getColor(styleable.MenuLayout_menu_textDefaultColor, 0)
                        textHighlightColor = typedArray.getColor(styleable.MenuLayout_menu_textHighlightColor, 0)
                    } finally {
                        typedArray.recycle()
                    }
                })
    }

    fun setMenu(menuItems: List<MenuItem>) {
        mMenuItems = menuItems
        setUpMenu()
    }

    private fun setUpMenu() {
        if (mMenuItemViews.isNotEmpty()) {
            mMenuItemViews.clear()
            removeAllViews()
        }


        mMenuItems!!.forEachIndexed { index, item ->
            addView(MenuItemView(context, resources.getString(item.titleResId), mMenuItemConfiguration.get()!!)
                    .apply {
                        mMenuItemViews.add(this)
                        if (mCurrentActionId == item.actionId) {
                            mCurrentMenuItemIndex = index
                            doOnGlobalLayout {
                                highlight(true)
                            }
                        }
                        setOnClickListener {
                            if (mCurrentMenuItemIndex != index) {
                                mMenuItemViews[mCurrentMenuItemIndex].highlight(false)
                                mCurrentMenuItemIndex = index
                                mCurrentActionId = item.actionId
                                mMenuItemViews[mCurrentMenuItemIndex].highlight(true)
                            }
                        }
                    }, generateDefaultLayoutParams()
                    .apply {
                        marginStart = mMenuItemConfiguration.get()!!.textGap
                    })
        }
    }

    data class MenuItem(var actionId: Int, var titleResId: Int)
}