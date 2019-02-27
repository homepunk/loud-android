package homepunk.github.com.presentation.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import homepunk.github.com.presentation.core.listener.OnItemClickListener

/**Created by Homepunk on 10.01.2019. **/
abstract class BaseRecyclerViewAdapter<ITEM, VH : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>> : RecyclerView.Adapter<VH>() {
    private var focusedItem = 0

    open var itemList: List<ITEM> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    open var onItemClickListener: OnItemClickListener<ITEM>? = null

    override fun getItemCount() = itemList.size

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
        }
        holder.root.isSelected = focusedItem == position
    }


    open fun <T : ViewDataBinding> inflateVH(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!
    abstract class BaseViewHolder<ITEM>(val root: View) : RecyclerView.ViewHolder(root) {
        abstract fun bind(item: ITEM)
    }
}