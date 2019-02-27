package homepunk.github.com.presentation.common.model.menu

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import homepunk.github.com.domain.model.AppMode

/**Created by Homepunk on 17.01.2019. **/
data class MenuModeModel(val menuType: Int,
                         @ColorRes val colorResId: Int,
                         @StringRes val titleResId: Int,
                         @DrawableRes val iconResId: Int,
                         val appMode: AppMode? = null) {

    val isModeSelected = appMode != null

    object TYPE {
        const val GEAR = 0
        const val EVENTS = 1
        const val LIBRARY = 2
        const val SETTINGS = 3
    }
}