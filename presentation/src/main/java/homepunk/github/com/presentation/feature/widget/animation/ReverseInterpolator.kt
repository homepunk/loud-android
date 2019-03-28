package homepunk.github.com.presentation.feature.widget.animation

/**Created by Homepunk on 31.01.2019. **/
import android.view.animation.Interpolator

class ReverseInterpolator(var interpolator: Interpolator) : Interpolator {
    override fun getInterpolation(input: Float): Float {
        return Math.abs(1f - interpolator.getInterpolation(input))
    }
}