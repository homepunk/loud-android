package homepunk.github.com.presentation.core.listener

/**Created by Homepunk on 27.03.2019. **/
interface OnParentChildClickListener<CHILD, PARENT> {
    fun onClick(position: Int, parent: PARENT, childPosition: Int, child: CHILD)
}