package homepunk.github.com.presentation.common.model.mode

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**Created by Homepunk on 16.01.2019. **/
data class AppModeModel(val mode: Mode,
                        @StringRes val title: Int,
                        @ColorRes val colorResId: Int,
                        @DrawableRes val iconResId: Int) {
    enum class Mode {
        GEAR, EVENT, LIBRARY, ALL
    }
}