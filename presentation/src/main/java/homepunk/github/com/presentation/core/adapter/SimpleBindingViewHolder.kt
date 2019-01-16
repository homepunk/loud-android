package homepunk.github.com.presentation.core.adapter

import androidx.databinding.ViewDataBinding
import homepunk.github.com.presentation.core.base.BaseRecyclerViewAdapter

/**Created by Homepunk on 11.01.2019. **/
class SimpleBindingViewHolder<ITEM>(val binding: ViewDataBinding, val variableId: Int) : BaseRecyclerViewAdapter.BaseViewHolder<ITEM>(binding.root) {
    override fun bind(item: ITEM) {
        with(binding) {
            setVariable(variableId, item)
        }
    }
}