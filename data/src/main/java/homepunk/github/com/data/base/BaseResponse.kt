package homepunk.github.com.data.base

abstract class BaseResponse<T> {
    val status: String = ""
    val data: T? = null
}