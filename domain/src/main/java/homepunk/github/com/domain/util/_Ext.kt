package homepunk.github.com.domain.util

/**Created by Homepunk on 11.07.2019. **/
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
