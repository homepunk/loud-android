package homepunk.github.com.presentation.common.adapter

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import homepunk.github.com.presentation.core.base.BaseRecyclerViewAdapter


/**Created by Homepunk on 11.01.2019. **/
class SimpleBindingRecyclerAdapter<ITEM>(private val layoutId: Int, val variableId: Int) : BaseRecyclerViewAdapter<ITEM, SimpleBindingRecyclerAdapter.SimpleBindingViewHolder<ITEM>>() {
    constructor(layoutId: Int, variableId: Int, itemList: List<ITEM>) : this(layoutId, variableId) {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleBindingViewHolder<ITEM> {
        return SimpleBindingViewHolder(inflateVH(parent, layoutId), variableId)
    }

    class SimpleBindingViewHolder<ITEM>(val binding: ViewDataBinding, val variableId: Int) : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>(binding.root) {
        override fun bind(item: ITEM) {
            with(binding) {
                setVariable(variableId, item)
            }
        }
    }
}