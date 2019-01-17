package homepunk.github.com.presentation.common.model

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

/**Created by Homepunk on 16.01.2019. **/
data class AppModeModel(val appMode: AppMode,
                        val title: String,
                        @ColorRes val colorResId: Int,
                        @DrawableRes val iconResId: Int)