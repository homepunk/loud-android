package homepunk.github.com.vinyl_underground.util

import android.content.Context
import android.util.TypedValue

class DimensionUtil {
    companion object {
        fun <T> dpToPx(context: Context, value: Float) : T {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics) as T
        }
    }
}