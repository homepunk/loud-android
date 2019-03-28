package homepunk.github.com.presentation.core.ext

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 23.01.2019. **/

fun ObservableBoolean.swap() = set(!get())


fun <T> ObservableArrayList<T>.addAllToEmptyList(collection: List<T>) {
    clear()
    addAll(collection)
}