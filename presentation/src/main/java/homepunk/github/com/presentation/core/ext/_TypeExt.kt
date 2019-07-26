package homepunk.github.com.presentation.core.ext

/**Created by Homepunk on 09.07.2019. **/

fun String?.or(text: String): String {
    return if (!this.isNullOrEmpty()) this else text
}