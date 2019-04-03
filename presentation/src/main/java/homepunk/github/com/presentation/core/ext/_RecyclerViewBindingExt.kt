@file:Suppress("UNCHECKED_CAST")

package homepunk.github.com.presentation.core.ext

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.common.adapter.ExpandableBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingChildModel
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingParentModel
import homepunk.github.com.presentation.core.base.BaseRecyclerViewAdapter
import homepunk.github.com.presentation.core.listener.OnItemPositionClickListener
import homepunk.github.com.presentation.core.listener.OnParentChildClickListener
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration
import timber.log.Timber

/**Created by Homepunk on 14.01.2019. **/

@BindingAdapter("adapter")
fun <T> RecyclerView.bindAdapter(adapter: SimpleBindingRecyclerAdapter<T>) {
    this.adapter = adapter
}

@BindingAdapter("itemList")
fun <T> RecyclerView.bindItemList(itemList: List<T>) {
    (adapter as? SimpleBindingRecyclerAdapter<T>)?.let {
        it.itemList = itemList
    }
}

@BindingAdapter("expandableItemList")
fun <CHILD : ExpandableBindingChildModel, PARENT : ExpandableBindingParentModel<CHILD>> RecyclerView.bindParentList(itemList: ObservableArrayList<PARENT>) {
    Timber.w("BIND NEW PARENT LIST: ${itemList.size}")
    (adapter as? ExpandableBindingRecyclerAdapter<CHILD, PARENT>)?.let {
        if (!it.isParentListInitialized()) {
            Timber.w("INITIALIZE")
            it.setParentList(itemList)
        }
    }
}

@BindingAdapter("onParentChildClickListener")
fun <CHILD : ExpandableBindingChildModel, PARENT : ExpandableBindingParentModel<CHILD>> RecyclerView.setOnParentChildClickListener(listener: OnParentChildClickListener<CHILD, PARENT>) {
    (adapter as? ExpandableBindingRecyclerAdapter<CHILD, PARENT>)?.let {
        it.onParentChildClickListener = listener
    }
}

@BindingAdapter(
        requireAll = false,
        value = [
            "itemDecoration_startLeft",
            "itemDecoration_startTop",
            "itemDecoration_left",
            "itemDecoration_right",
            "itemDecoration_top",
            "itemDecoration_bottom"])
fun RecyclerView.bindItemDecortaion(startLeft: Float = 0f,
                                    startTop: Float = 0f,
                                    left: Float = 0f,
                                    right: Float = 0f,
                                    top: Float = 0f,
                                    bottom: Float = 0f) {
    this.addItemDecoration(MarginItemDecoration(startLeft.toInt(), startTop.toInt(), left.toInt(), right.toInt(), top.toInt(), bottom.toInt()))
}

fun RecyclerView.setupWithViewPager(viewPager: ViewPager) {
    if (adapter as? BaseRecyclerViewAdapter<*, *> == null) {
        throw UnsupportedOperationException("Please make sure that recycler adapter is inherited from BaseRecyclerViewAdapter and adapter is already bound to recycler")
    } else {
        (adapter as? BaseRecyclerViewAdapter<*, *>)?.let { rvAdapter ->
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                var prevMenuItem: MenuItem? = null
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    prevMenuItem?.isChecked = false
                    rvAdapter.highlightItem(position)
                }
            })

            rvAdapter.onItemPositionClickListener = object : OnItemPositionClickListener {
                override fun onClick(position: Int) {
                    viewPager.currentItem = position
                }
            }
        }
    }
}



