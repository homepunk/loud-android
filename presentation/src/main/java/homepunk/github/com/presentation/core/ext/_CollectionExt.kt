package homepunk.github.com.presentation.core.ext

/**Created by Homepunk on 25.01.2019. **/

fun Array<String>.getMaxLenString(): String {
    var maxLenText = ""
    forEach {
        if (it.length > maxLenText.length) {
            maxLenText = it
        }
    }
    return maxLenText
}

fun Array<Pair<Int, String>>.getMaxLenString(): String {
    var maxLenText = ""
    forEach {
        if (it.second.length > maxLenText.length) {
            maxLenText = it.second
        }
    }
    return maxLenText
}



fun Array<String>.getMaxLenStringIndex(): Int {
    var maxLenText = ""
    var maxLenTextIndex = 0
    forEachIndexed { i, it ->
        if (it.length > maxLenText.length) {
            maxLenText = it
            maxLenTextIndex = i
        }
    }
    return maxLenTextIndex
}