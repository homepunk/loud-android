package homepunk.github.com.presentation.core.base

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

/**Created by Homepunk on 13.05.2019. **/
abstract class BaseBindingRecyclerViewAdapter<ITEM, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {
    lateinit var itemList: ObservableList<ITEM>

    fun isParentListInitialized() = ::itemList.isInitialized

    fun setItemList(itemList: ObservableArrayList<ITEM>) {
        this.itemList = itemList
        this.itemList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<ITEM>?>() {
            override fun onChanged(sender: ObservableList<ITEM>?) {
                notifyDataSetChanged()
                Timber.w("onChanged")
            }

            override fun onItemRangeRemoved(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
                Timber.w("onItemRangeRemoved $positionStart, $itemCount")
                notifyItemRemoved(positionStart)
            }

            override fun onItemRangeMoved(sender: ObservableList<ITEM>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                Timber.w("onItemRangeMoved")
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
                Timber.w("onItemRangeInserted")
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(sender: ObservableList<ITEM>?, positionStart: Int, itemCount: Int) {
                Timber.w("onItemRangeChanged")
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })
    }

    override fun getItemCount() = if (isParentListInitialized()) itemList.size else 0
}