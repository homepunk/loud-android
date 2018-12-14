package homepunk.github.com.vinyl_underground.util

import android.graphics.Rect
import android.view.View
import android.view.TouchDelegate



object TouchDelegateUtil {
    fun addTouchDElegate(view: View) {
        val parent = view.parent as View
        parent.post {
            val r = Rect()
            view.getHitRect(r)
            r.top += DimensionUtil.dpToPx<Int>(view.context, 24f)
            parent.touchDelegate = TouchDelegate(r, view)
        }
    }
}