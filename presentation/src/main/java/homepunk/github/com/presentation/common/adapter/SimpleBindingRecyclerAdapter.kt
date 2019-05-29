package homepunk.github.com.presentation.common.adapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import homepunk.github.com.presentation.core.base.BaseRecyclerViewAdapter
import homepunk.github.com.presentation.core.listener.SpecificFieldClickListener


/**Created by Homepunk on 11.01.2019. **/
class SimpleBindingRecyclerAdapter<ITEM>(private val layoutId: Int, val variableId: Int) : BaseRecyclerViewAdapter<ITEM, SimpleBindingRecyclerAdapter.SimpleBindingViewHolder<ITEM>>() {
    var onSpecificFieldClickListener: SpecificFieldClickListener<ITEM>? = null

    constructor(layoutId: Int, variableId: Int, itemList: List<ITEM>) : this(layoutId, variableId) {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleBindingViewHolder<ITEM> {
        return SimpleBindingViewHolder(inflateVH(parent, layoutId), variableId)
    }

    override fun onBindViewHolder(holder: SimpleBindingViewHolder<ITEM>, position: Int) {
        super.onBindViewHolder(holder, position)
        onSpecificFieldClickListener?.let { listener ->
            holder.binding.root.findViewById<View>(listener.viewId).setOnClickListener {
                listener.onClick(position, itemList[position])
            }
        }
    }

    class SimpleBindingViewHolder<ITEM>(val binding: ViewDataBinding, val variableId: Int) : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>(binding.root) {
        override fun bind(item: ITEM) {
            with(binding) {
                setVariable(variableId, item)
            }
        }
    }
}