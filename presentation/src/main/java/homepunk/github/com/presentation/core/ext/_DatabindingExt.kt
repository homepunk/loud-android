package homepunk.github.com.presentation.core.ext

import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 23.01.2019. **/

fun ObservableBoolean.swap() = set(!get())
