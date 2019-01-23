package homepunk.github.com.presentation.feature.widget.tablayout

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.TextView
import homepunk.github.com.presentation.R



/**Created by Homepunk on 22.01.2019. **/
class BubbleTab(context: Context) : FrameLayout(context) {
    var tabText: TextView
    var tabBackground: FrameLayout

    init {
        val tabLayout = LayoutInflater.from(context).inflate(R.layout.item_bubble_tab, this, true)
        tabText = tabLayout.findViewById(R.id.title)
        tabBackground = tabLayout.findViewById(R.id.background)
    }

    fun updateBackgroundWithoutBorder(color: Int) {
        val gdDefault = GradientDrawable()
        gdDefault.setColor(color)
        gdDefault.cornerRadii = floatArrayOf(360f, 360f, 360f, 360f, 360f, 360f, 360f, 360f)
        tabBackground.background = gdDefault
    }
}