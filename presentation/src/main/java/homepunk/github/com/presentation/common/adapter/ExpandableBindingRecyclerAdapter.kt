package homepunk.github.com.presentation.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
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

/**Created by Homepunk on 18.03.2019. **/
class ExpandableBindingRecyclerAdapter<CHILD : ExpandableBindingChildModel, PARENT : ExpandableBindingParentModel<CHILD>>
    : RecyclerView.Adapter<ExpandableViewHolder<CHILD, PARENT>>() {

    open var onParentClickListener: OnItemClickListener<PARENT>? = null
    open var onParentChildClickListener: OnParentChildClickListener<CHILD, PARENT>? = null

    open var parentList: List<PARENT> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = parentList.size

    override fun onBindViewHolder(holder: ExpandableViewHolder<CHILD, PARENT>, position: Int) {
        holder.bind(parentList[position], onParentChildClickListener)
        holder.binding.root.setOnClickListener {
            onParentClickListener?.onClick(position, parentList[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpandableViewHolder<CHILD, PARENT>(inflateVH(parent, R.layout.custom_layout_item_expandable_parent))

    private fun <T : ViewDataBinding> inflateVH(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!

    class ExpandableViewHolder<CHILD : ExpandableBindingChildModel, PARENT : ExpandableBindingParentModel<CHILD>>(val binding: CustomLayoutItemExpandableParentBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(parent: PARENT, onParentChildClickListener: OnParentChildClickListener<CHILD, PARENT>?) {
            with(binding) {
                val root = inflateParentLayout(parent)
                container.addView(root, 0)

                parent.children.forEachIndexed { index, child ->
                    val root = inflateChildLayout(child)
                    root.setOnClickListener { onParentChildClickListener?.onClick(index, child, parent) }
                    childrenLayout.addView(root)
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

        private fun inflateParentLayout(parent: PARENT): View {
            val parentLayoutItemBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(binding.root.context), parent.getLayoutId(), null, false)
            parentLayoutItemBinding.setVariable(parent.getBindingVariableId(), parent)
            parentLayoutItemBinding.root.setOnClickListener { parent.isParentExpanded.swap() }
            return parentLayoutItemBinding.root
        }
    }
}
