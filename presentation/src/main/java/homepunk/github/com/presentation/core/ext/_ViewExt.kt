package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.LinearInterpolator
import androidx.core.widget.NestedScrollView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.feature.widget.animation.AnimationEventLiveData
import homepunk.github.com.presentation.feature.widget.expandablelayout.ExpandableHeader
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

@BindingAdapter("isVisible")
fun View.isVisible(isVisible: Boolean) {
    Timber.w("isVisible = $isVisible")
    visibility = if (isVisible) VISIBLE else GONE
}

@BindingAdapter("scrollToPosition", "scrollToRecyclerId", requireAll = true)
fun NestedScrollView.scrollToRecyclerChild(position: Int, scrollAnchorId: Int) {
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
//                    Timber.w("SCROLL TO $y")
                    scrollTo(0, y)
                }
                animator.addListener(object : AnimatorListenerWrapper() {
                    override fun onAnimationStart(animation: Animator?) {
                        AnimationEventLiveData.getInstance().isScrollToPosition = true
                        Timber.w("SCROLL TO ITEM isScrollToPosition = true")
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        AnimationEventLiveData.getInstance().isScrollToPosition = false
                        Timber.w("SCROLL TO ITEM isScrollToPosition = false")
                    }
                })
                animator.start()

            }
        }
    }
}

//@BindingAdapter("timeline_list")
//fun <T> TimelineView<T>.bindTimelineList(itemList: List<TimelineItem<T>>) {
//    setUpTimeline(itemList)
//}

@BindingAdapter("el_title")
fun ExpandableHeader.bindElTitle(title: String) {
    setHeaderTitle(title)
}


fun View.isNotVisible(): Boolean = visibility == GONE || visibility == View.INVISIBLE

fun View.isVisible(): Boolean = visibility == VISIBLE

//@BindingAdapter("background")
//fun View.setBackgroundColorRes(@ColorRes colorRes: Int) {
//    background = ColorDrawable(ContextCompat.getColor(context, colorRes))
//}
//
//@BindingAdapter("textColor")
//fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
//    setTextColor(ContextCompat.getColor(context, colorRes))
//}

