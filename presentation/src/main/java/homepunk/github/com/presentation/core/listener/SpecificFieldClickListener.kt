package homepunk.github.com.presentation.core.listener

/**Created by Homepunk on 27.05.2019. **/
abstract class SpecificFieldClickListener<T>(var viewId: Int) {
    abstract fun onClick(position: Int, item: T)
}