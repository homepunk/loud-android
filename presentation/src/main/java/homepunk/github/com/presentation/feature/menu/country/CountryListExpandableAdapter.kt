package homepunk.github.com.presentation.feature.menu.country

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.model.ExpandableChildModel
import homepunk.github.com.presentation.core.base.BaseBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.databinding.CustomLayoutItemExpandableParentBinding
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel
import homepunk.github.com.presentation.util.LayoutUtil

/**Created by Homepunk on 26.07.2019. **/
class CountryListExpandableAdapter : BaseBindingRecyclerViewAdapter<CountryBindingParentModel, CountryListExpandableAdapter.ExpandableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ExpandableViewHolder(LayoutUtil.inflateBindingLayout(parent, R.layout.custom_layout_item_expandable_parent))

    override fun onBindViewHolder(holder: ExpandableViewHolder, position: Int) {
    }

    class ExpandableViewHolder(val binding: CustomLayoutItemExpandableParentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(parent: CountryBindingParentModel, position: Int, onParentChildClickListener: OnParentChildClickListener<CountryBindingParentModel, CityBindingChildModel>?, onParentClickListener: OnItemClickListener<CountryBindingParentModel>?, onChildClickListener: OnItemClickListener<CityBindingChildModel>?) {
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
                                    onChildClickListener?.onClick(index, child)
                                    onParentChildClickListener?.onClick(position, parent, index, child)
                                }
                            })
                }

                parentModel = parent
            }
        }

        private fun inflateChildLayout(child: ExpandableChildModel): View {
            val childLayoutBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(binding.root.context), child.getLayoutId(), null, false)
            childLayoutBinding.setVariable(child.getBindingVariableId(), child)
            val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            childLayoutBinding.root.layoutParams = params
            return childLayoutBinding.root
        }

        private fun inflateParentLayout(parent: CountryBindingParentModel, position: Int, onParentClickListener: OnItemClickListener<CountryBindingParentModel>?): View {
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