package homepunk.github.com.presentation.core.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**Created by Homepunk on 10.01.2019. **/
abstract class BaseRecyclerViewAdapter<ITEM, VH : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>> : RecyclerView.Adapter<VH>() {
    open var itemList: List<ITEM> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(itemList[position])
    }

    open fun <T : ViewDataBinding> inflateVH(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!

    abstract class BaseViewHolder<ITEM>(val root: View) : RecyclerView.ViewHolder(root) {
        abstract fun bind(item: ITEM)
    }
}