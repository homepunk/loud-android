package homepunk.github.com.presentation.core.adapter

import android.view.ViewGroup
import homepunk.github.com.presentation.core.base.BaseRecyclerViewAdapter


/**Created by Homepunk on 11.01.2019. **/
class SimpleBindingRecyclerViewAdapter<ITEM>(private val layoutId: Int, val variableId: Int) : BaseRecyclerViewAdapter<ITEM, SimpleBindingViewHolder<ITEM>>() {
    constructor(layoutId: Int, variableId: Int, itemList: List<ITEM>) : this(layoutId, variableId) {
        this.itemList = itemList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleBindingViewHolder<ITEM> {
        return SimpleBindingViewHolder(inflateVH(parent, layoutId), variableId)
    }

}