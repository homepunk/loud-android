package homepunk.github.com.presentation.feature.widget.dropdownlistview

/**Created by Homepunk on 10.07.2019. **/

interface OnDropDownClickListener<T> {
    fun onClick(position: Int, item: T)

    fun onSelect(position: Int, item: T)
}