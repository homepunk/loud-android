package homepunk.github.com.presentation.util

import android.content.Context
import android.util.TypedValue
@Suppress("UNCHECKED_CAST")
class DimensionUtil {
    companion object {
        fun <T> dpToPx(context: Context, value: Float) : T {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics) as T
        }
    }
}