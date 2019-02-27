package homepunk.github.com.presentation.core.listener

/**Created by Homepunk on 26.02.2019. **/
interface OnItemClickListener<T> {
    fun onClick(position: Int, item: T)
}