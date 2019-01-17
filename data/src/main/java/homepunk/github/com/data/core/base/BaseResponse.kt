package homepunk.github.com.data.core.base

abstract class BaseResponse<T> {
    val status: String = ""
    val data: T? = null
}