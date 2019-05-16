package homepunk.github.com.presentation.core.listener

/**Created by Homepunk on 14.05.2019. **/
interface OnExpandableListClickListener<PARENT, ITEM> {
    fun onClick(position: Int, item: ITEM, parent: PARENT)
}