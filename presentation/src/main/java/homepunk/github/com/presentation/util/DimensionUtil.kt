package homepunk.github.com.presentation.util

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
@Suppress("UNCHECKED_CAST")
class DimensionUtil {
    companion object {
        val screenWidth = Resources.getSystem().displayMetrics.widthPixels
        val scerenHeight = Resources.getSystem().displayMetrics.heightPixels

        fun <T> dpToPx(context: Context, value: Float) : T {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics) as T
        }

    }
}