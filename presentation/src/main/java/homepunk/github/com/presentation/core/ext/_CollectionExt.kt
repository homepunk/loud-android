package homepunk.github.com.presentation.core.ext

import androidx.databinding.ObservableArrayList

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


fun <E> List<E>.containsLike(predicate: (E) -> Boolean): Boolean {
    forEachIndexed { index, item ->
        if (predicate.invoke(item)) {
            return true
        }
    }
    return false
}


fun <E> MutableList<E>.removeWhen(predicate: (E) -> Boolean): Boolean{
    with(iterator()) {
        while (hasNext()) {
            if (predicate.invoke(next())) {
                remove()
                return true
            }
        }
    }
    return false
}


fun <E> Collection<E>.toArrayList(): java.util.ArrayList<E> {
    return ArrayList(this)
}


fun <T> ObservableArrayList<T>.addAllFromIndex(startIndex: Int, source: java.util.ArrayList<T>) {
    addAll(source.subList(startIndex, source.size - 1))
}

fun <T> ArrayList<T>.subList(fromIndex: Int): MutableList<T> {
    return if (fromIndex >= size) mutableListOf() else subList(fromIndex, size)
}

fun String.asList(): List<String> {
    return mutableListOf(this)
}

