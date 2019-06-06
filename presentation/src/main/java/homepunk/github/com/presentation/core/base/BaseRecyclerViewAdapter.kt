package homepunk.github.com.presentation.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.OnItemPositionClickListener
import timber.log.Timber

/**Created by Homepunk on 10.01.2019. **/
abstract class BaseRecyclerViewAdapter<ITEM, VH : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>> : RecyclerView.Adapter<VH>() {
    private var focusedItem = 0

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
                notifyItemRangeRemoved(positionStart, itemCount)
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

    open var onItemClickListener: OnItemClickListener<ITEM>? = null
    open var onItemPositionClickListener: OnItemPositionClickListener? = null

//    override fun getItemCount() = itemList.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(itemList[position])

        holder.root.setOnClickListener {
            notifyItemChanged(focusedItem)
            focusedItem = position
            notifyItemChanged(focusedItem)
            onItemClickListener?.onClick(position, itemList[position])
            onItemPositionClickListener?.onClick(position)
        }
        holder.root.isSelected = focusedItem == position
    }


    fun highlightItem(position: Int) {
        notifyItemChanged(focusedItem)
        focusedItem = position
        notifyItemChanged(focusedItem)
    }

    open fun <T : ViewDataBinding> inflateVH(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!

    abstract class BaseViewHolder<ITEM>(val root: View) : RecyclerView.ViewHolder(root) {
        abstract fun bind(item: ITEM)
    }
}