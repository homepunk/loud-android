package homepunk.github.com.presentation.feature.widget.tablayout

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import homepunk.github.com.presentation.R


/**Created by Homepunk on 22.01.2019. **/
class BubbleTab(context: Context) : FrameLayout(context) {
    var image: ImageView
    var title: TextView
    var tabBackground: LinearLayout

    init {
        val tabLayout = LayoutInflater.from(context).inflate(R.layout.item_bubble_tab, this, true)
        title = tabLayout.findViewById(R.id.title)
        image = tabLayout.findViewById(R.id.image)
        tabBackground = tabLayout.findViewById(R.id.background)
    }

    fun updateBackgroundWithoutBorder(color: Int) {
        val gdDefault = GradientDrawable()
        gdDefault.setColor(color)
        gdDefault.cornerRadii = floatArrayOf(360f, 360f, 360f, 360f, 360f, 360f, 360f, 360f)
        tabBackground.background = gdDefault
    }

    fun setTint(color: Int) {
        ImageViewCompat.setImageTintList(image, ColorStateList.valueOf(color))
        title.setTextColor(color)
    }

    fun updateBackground(resId: Int) {
//        val gdDefault = GradientDrawable()
//        gdDefault.setColor(color)
//        gdDefault.cornerRadii = floatArrayOf(360f, 360f, 360f, 360f, 360f, 360f, 360f, 360f)
        tabBackground.setBackgroundResource(resId)
    }
}