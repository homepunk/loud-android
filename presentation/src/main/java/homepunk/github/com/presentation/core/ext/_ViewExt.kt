package homepunk.github.com.presentation.core.ext

import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException

/**Created by Homepunk on 11.01.2019. **/

fun View.getDrawableFromXml(id: Int): Drawable? {
    var drawable: Drawable? = null
    try {
        drawable = Drawable.createFromXml(resources, resources.getXml(id))
    } catch (e: XmlPullParserException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return drawable
}

fun View.swapVisibility() {
    visibility = if (visibility == GONE) {
        VISIBLE
    } else {
        GONE
    }
}

//@BindingAdapter("background")
//fun View.setBackgroundColorRes(@ColorRes colorRes: Int) {
//    background = ColorDrawable(ContextCompat.getColor(context, colorRes))
//}
//
//@BindingAdapter("textColor")
//fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
//    setTextColor(ContextCompat.getColor(context, colorRes))
//}

