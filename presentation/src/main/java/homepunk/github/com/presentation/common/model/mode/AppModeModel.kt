package homepunk.github.com.presentation.common.model.mode

import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import homepunk.github.com.domain.model.AppMode

/**Created by Homepunk on 16.01.2019. **/
data class AppModeModel(val mode: AppMode,
                        val title: String,
                        @ColorInt val color: Int,
                        @DrawableRes val iconResId: Int,
                        @StyleRes val themeResId: Int)