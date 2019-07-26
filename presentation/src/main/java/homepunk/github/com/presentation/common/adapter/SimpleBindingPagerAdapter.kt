package homepunk.github.com.presentation.common.adapter

import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter
import homepunk.github.com.presentation.util.LayoutUtil


/**Created by Homepunk on 24.07.2019. **/
class SimpleBindingPagerAdapter<T>(var layoutId: Int, var variableId: Int, var itemList: ObservableArrayList<T>) : PagerAdapter() {

    override fun instantiateItem(@NonNull collection: ViewGroup, position: Int): Any {
        val binding = LayoutUtil.inflateBindingLayout<ViewDataBinding>(collection, layoutId)
        binding.setVariable(variableId, itemList[position])
        collection.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun getCount() = itemList.size
}