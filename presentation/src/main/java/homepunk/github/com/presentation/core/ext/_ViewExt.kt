package homepunk.github.com.presentation.core.ext

import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.LinearInterpolator
import android.widget.ScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import org.xmlpull.v1.XmlPullParserException
import timber.log.Timber
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

@BindingAdapter("scrollToPosition", "scrollToRecyclerId", requireAll = true)
fun ScrollView.scrollToRecyclerChild(position: Int, scrollAnchorId: Int) {
    if (position != -1) {
        post {
            getChildAt(0).findViewById<RecyclerView>(scrollAnchorId).let {
                val height = (it.y + it.getChildAt(position).y).toInt()
                val duration = Math.abs(scrollY - height) / context.resources.displayMetrics.density
                Timber.w("SCROLL TO ITEM duration = $duration, from y = $scrollY, to y = $height")
                val animator = ValueAnimator.ofInt(scrollY, height)
                animator.duration = duration.toLong()
                animator.interpolator = LinearInterpolator()
                animator.addUpdateListener {
                    val y = it.animatedValue as Int
                    Timber.w("SCROLL TO $y")
                    scrollTo(0, y)
                }
                animator.start()
            }
        }
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

