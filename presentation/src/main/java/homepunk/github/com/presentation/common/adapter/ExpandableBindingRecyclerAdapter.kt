package homepunk.github.com.presentation.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.ExpandableBindingRecyclerAdapter.ExpandableViewHolder
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingChildModel
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingParentModel
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.databinding.CustomLayoutItemExpandableParentBinding
import timber.log.Timber

/**Created by Homepunk on 18.03.2019. **/
open class ExpandableBindingRecyclerAdapter<CHILD : ExpandableBindingChildModel, PARENT : ExpandableBindingParentModel<CHILD>>
    : RecyclerView.Adapter<ExpandableViewHolder<CHILD, PARENT>>() {

    var onChildClickListener: OnItemClickListener<CHILD>? = null
    var onParentClickListener: OnItemClickListener<PARENT>? = null
    var onParentChildClickListener: OnParentChildClickListener<CHILD, PARENT>? = null

    lateinit var parentList: ObservableList<PARENT>

    fun isParentListInitialized() = ::parentList.isInitialized

    fun setParentList(parentList: ObservableArrayList<PARENT>) {
        this.parentList = parentList
        this.parentList.addOnListChangedCallback(object: ObservableList.OnListChangedCallback<ObservableList<PARENT>?>() {
            override fun onChanged(sender: ObservableList<PARENT>?) {
                notifyDataSetChanged()
                Timber.w("onChanged")            }

            override fun onItemRangeRemoved(sender: ObservableList<PARENT>?, positionStart: Int, itemCount: Int) {
                Timber.w("onItemRangeRemoved $positionStart, $itemCount")
                notifyItemRemoved(positionStart)
            }

            override fun onItemRangeMoved(sender: ObservableList<PARENT>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                Timber.w("onItemRangeMoved")
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableList<PARENT>?, positionStart: Int, itemCount: Int) {
                Timber.w("onItemRangeInserted")
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(sender: ObservableList<PARENT>?, positionStart: Int, itemCount: Int) {
                Timber.w("onItemRangeChanged")
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })
    }

    override fun getItemCount() = if (isParentListInitialized()) parentList.size else 0

    override fun onBindViewHolder(holder: ExpandableViewHolder<CHILD, PARENT>, position: Int) {
        Timber.w("BIND: ${parentList.size}, position = $position")
        holder.bind(parentList[position], position, onParentChildClickListener, onParentClickListener, onChildClickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpandableViewHolder<CHILD, PARENT>(inflateVH(parent, R.layout.custom_layout_item_expandable_parent))

    private fun <T : ViewDataBinding> inflateVH(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!

    class ExpandableViewHolder<CHILD : ExpandableBindingChildModel, PARENT : ExpandableBindingParentModel<CHILD>>(val binding: CustomLayoutItemExpandableParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(parent: PARENT, position: Int, onParentChildClickListener: OnParentChildClickListener<CHILD, PARENT>?, onParentClickListener: OnItemClickListener<PARENT>?, onChildClickListener: OnItemClickListener<CHILD>?) {
            with(binding) {
                if (parentLayout.childCount == 2) {
                    parentLayout.removeViewAt(0)
                    childrenLayout.removeAllViews()
                }

                with(inflateParentLayout(parent, position, onParentClickListener)) {
                    parentLayout.addView(this, 0)
                }

                parent.children.forEachIndexed { index, child ->
                    childrenLayout.addView(inflateChildLayout(child)
                            .apply {
                                setOnClickListener {
                                    onChildClickListener?.onClick(position, child)
                                    onParentChildClickListener?.onClick(position, parent, index, child) }
                            })
                }

                parentModel = parent
            }
        }

        private fun inflateChildLayout(child: ExpandableBindingChildModel): View {
            val childLayoutBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(binding.root.context), child.getLayoutId(), null, false)
            childLayoutBinding.setVariable(child.getBindingVariableId(), child)
            val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            childLayoutBinding.root.layoutParams = params
            return childLayoutBinding.root
        }

        private fun inflateParentLayout(parent: PARENT, position: Int, onParentClickListener: OnItemClickListener<PARENT>?): View {
            val parentLayoutItemBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(binding.root.context), parent.getLayoutId(), null, false)
            parentLayoutItemBinding.setVariable(parent.getBindingVariableId(), parent)
            parentLayoutItemBinding.root.setOnClickListener {
                parent.isParentExpanded.swap()
                onParentClickListener?.onClick(position, parent)
            }
            return parentLayoutItemBinding.root
        }
    }
}
