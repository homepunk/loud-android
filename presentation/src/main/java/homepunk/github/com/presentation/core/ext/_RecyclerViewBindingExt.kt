package homepunk.github.com.presentation.core.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration

/**Created by Homepunk on 14.01.2019. **/

@BindingAdapter("adapter")
fun <T> RecyclerView.bindAdapter(adapter: SimpleBindingRecyclerViewAdapter<T>) {
    this.adapter = adapter
}

@BindingAdapter("itemList")
fun <T> RecyclerView.bindItemList(itemList: List<T>) {
    (adapter as? SimpleBindingRecyclerViewAdapter<T>)?.let {
        it.itemList = itemList
    }
}

@BindingAdapter(
        requireAll = false,
        value = [
            "itemDecoration_startLeft",
            "itemDecoration_startTop",
            "itemDecoration_left",
            "itemDecoration_right",
            "itemDecoration_top",
            "itemDecoration_bottom"])
fun RecyclerView.bindItemDecortaion(startLeft: Float = 0f,
                                    startTop: Float = 0f,
                                    left: Float = 0f,
                                    right: Float = 0f,
                                    top: Float = 0f,
                                    bottom: Float = 0f) {
    this.addItemDecoration(MarginItemDecoration(startLeft.toInt(), startTop.toInt(), left.toInt(), right.toInt(), top.toInt(), bottom.toInt()))
}


