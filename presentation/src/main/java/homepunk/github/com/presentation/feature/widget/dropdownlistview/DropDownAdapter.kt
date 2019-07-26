package homepunk.github.com.presentation.feature.widget.dropdownlistview

/**Created by Homepunk on 18.07.2019. **/
class DropDownAdapter<T> {
    private var onDropDownDataSetChangeListener: OnDropDownDataSetChangeListener? = null
    private var itemList: List<T>? = null
    private var currentItemPosition: Int = 0

    fun setItemList(itemList: List<T>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    fun getItem(position: Int) = itemList?.get(position)

    fun notifyDataSetChanged() {
        onDropDownDataSetChangeListener?.onDataSetChanged()
    }

    interface OnDropDownDataSetChangeListener {
        fun onDataSetChanged()
    }
}