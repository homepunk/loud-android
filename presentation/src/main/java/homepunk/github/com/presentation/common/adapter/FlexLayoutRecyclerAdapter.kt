package homepunk.github.com.presentation.common.adapter

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.core.ext.dpToPx
import homepunk.github.com.presentation.feature.menu.country.model.CountryViewModel
import homepunk.github.com.presentation.util.DimensionUtil

/**Created by Homepunk on 26.07.2019. **/
class FlexLayoutRecyclerAdapter(layoutId: Int, variableId: Int = BR.model) : SimpleBindingRecyclerAdapter<CountryViewModel>(layoutId, variableId) {
    override fun onBindViewHolder(holder: SimpleBindingViewHolder<CountryViewModel>, position: Int) {
        with(holder.binding) {
            val model = itemList[position]
//            if (model.isParentExpanded.get()) {
//                root.layoutParams.width = MATCH_PARENT
//            } else {
//                root.layoutParams.width = (DimensionUtil.screenWidth - root.dpToPx<Int>(80f)) / 2
//            }
//            root.layoutParams.width = (DimensionUtil.screenWidth - root.dpToPx<Int>(80f)) / 2
            root.layoutParams.width = (DimensionUtil.screenWidth - root.dpToPx<Int>(80f)) / 2
            root.requestLayout()
        }
        super.onBindViewHolder(holder, position)
    }
}