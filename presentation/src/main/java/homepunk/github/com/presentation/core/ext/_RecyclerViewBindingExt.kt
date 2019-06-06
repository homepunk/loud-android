@file:Suppress("UNCHECKED_CAST")

package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.SimpleExpandableBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.model.ExpandableChildModel
import homepunk.github.com.presentation.common.adapter.model.ExpandableParentModel
import homepunk.github.com.presentation.core.base.BaseRecyclerViewAdapter
import homepunk.github.com.presentation.core.listener.OnItemPositionClickListener
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration
import timber.log.Timber


/**Created by Homepunk on 14.01.2019. **/

@BindingAdapter("adapter")
fun <T> RecyclerView.bindAdapter(adapter: SimpleBindingRecyclerAdapter<T>) {
    this.adapter = adapter
}

@BindingAdapter("changeColorOnClick")
fun TextView.changeColorOnClick(change: Boolean) {
    if (change) {
        setOnClickListener {
            if (currentTextColor == ContextCompat.getColor(context, R.color.pale_red2)) {
                setTextColor(ContextCompat.getColor(context, R.color.subtitle2))
                val animator = ValueAnimator.ofFloat(1f, 0.5f)
                animator.duration = 400
                animator.addUpdateListener {
                    alpha = it.animatedValue as Float
                    invalidate()
                }
                animator.start()
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.pale_red2))
                val animator = ValueAnimator.ofFloat(0.5f, 1f)
                animator.duration = 400
                animator.addUpdateListener {
                    alpha = it.animatedValue as Float
                    invalidate()
                }
                animator.start()
            }
        }
    }
}
@BindingAdapter("changeColorOnClick3")
fun TextView.changeColorOnClick3(change: Boolean) {
    if (change) {
        setOnClickListener {
            if (currentTextColor == ContextCompat.getColor(context, R.color.colorAccent)) {
                setTextColor(ContextCompat.getColor(context, R.color.whiteBone))
                val animator = ValueAnimator.ofFloat(1f, 0.6f)
                animator.duration = 1000
                animator.addUpdateListener {
                    alpha = it.animatedValue as Float
                    invalidate()
                }
                animator.start()
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
                val animator = ValueAnimator.ofFloat(0.6f, 1f)
                animator.duration = 1000
                animator.addUpdateListener {
                    alpha = it.animatedValue as Float
                    invalidate()
                }
                animator.start()
            }
        }
    }
}
@BindingAdapter("changeColorOnClick2")
fun TextView.changeColorOnClick2(change: Boolean) {
    if (change) {
        setOnClickListener {
            if (currentTextColor == ContextCompat.getColor(context, R.color.colorAccent)) {
                setTextColor(ContextCompat.getColor(context, R.color.subtitle2))
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            }
        }
    }
}

@BindingAdapter("snapHelper")
fun RecyclerView.snapHelper(enable: Boolean) {
    if (enable) {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(this)
    }
}

@BindingAdapter("itemList")
fun <T> RecyclerView.bindItemList(itemList: ObservableArrayList<T>) {
    if (layoutAnimation != null) {
        Timber.w("scheduleLayoutAnimation")
        scheduleLayoutAnimation()
    }
    (adapter as? SimpleBindingRecyclerAdapter<T>)?.let {
        if (!it.isParentListInitialized()) {
            Timber.w("INITIALIZE")
            it.setItemList(itemList)
        }
    }
}


@BindingAdapter("expandableItemList")
fun <CHILD : ExpandableChildModel, PARENT : ExpandableParentModel<CHILD>> RecyclerView.bindParentList(itemList: ObservableArrayList<PARENT>) {
    Timber.w("BIND NEW PARENT LIST: ${itemList.size}")
    (adapter as? SimpleExpandableBindingRecyclerAdapter<CHILD, PARENT>)?.let {
        if (!it.isParentListInitialized()) {
            Timber.w("INITIALIZE")
            it.setItemList(itemList)
        }
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
            "itemDecoration_bottom",
            "itemDecoration_lastRight"])
fun RecyclerView.bindItemDecortaion(startLeft: Float = 0f,
                                    startTop: Float = 0f,
                                    left: Float = 0f,
                                    right: Float = 0f,
                                    top: Float = 0f,
                                    bottom: Float = 0f,
                                    lastRight: Float = 0f) {
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

@BindingAdapter("moveToFitFullWidth")
fun RecyclerView.moveToPosition(position: Int) {
    if (position < 0) {
//        (layoutManager as CustomLinearLayoutManager).isHorizontalScrollEnabled = true
    } else {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == 0) {
//                    (layoutManager as CustomLinearLayoutManager).isHorizontalScrollEnabled = false
                    removeOnScrollListener(this)
                }
            }
        })

        val view = layoutManager?.findViewByPosition(position)
        val origXY = IntArray(2)

        if (position != adapter!!.itemCount - 1) {
            view?.getLocationOnScreen(origXY)
            smoothScrollBy(origXY[0], 0)
        } else {
            postDelayed({
                view?.getLocationOnScreen(origXY)
                val animator = ValueAnimator.ofInt(0, origXY[0])
                animator.addUpdateListener {
                    smoothScrollBy(it.animatedValue as Int, 0)
                }
                animator.duration = 100
                animator.interpolator = AccelerateInterpolator()
                animator.start()
            }, 300)
        }

    }
}

@BindingAdapter("moveAndRemove")
fun RecyclerView.moveAndRemove(position: Int) {
    if (position >= 0) {
        val view = layoutManager?.findViewByPosition(position + 1)
        val viewXY = IntArray(2)
        view?.getLocationOnScreen(viewXY)
        val animator = ValueAnimator.ofInt()

        if (position >= adapter!!.itemCount - 2) {
            animator.setIntValues(0, -viewXY[0])
        } else {
            animator.setIntValues(0, viewXY[0])
        }
        animator.addUpdateListener {
            smoothScrollBy(it.animatedValue as Int, 0)
        }
        animator.duration = 500
        animator.interpolator = AccelerateInterpolator()
        animator.start()
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == 0) {
                    with(adapter as SimpleBindingRecyclerAdapter<Any>) {
                        itemList.removeAt(position)
//                        notifyItemRemoved(position)
                    }
                    removeOnScrollListener(this)
                }
            }
        })

    }
}

@BindingAdapter(
        value = ["expand"],
        requireAll = false)
fun RecyclerView.expand(prevExpand: Boolean,
                        expand: Boolean) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    if (expand == prevExpand) {
        if (expand) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            visibility = View.VISIBLE
        } else {
            layoutParams.height = 0
            visibility = View.GONE
        }
        return
    } else {
        if (expand) {
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    val va: ObjectAnimator?
    if (expand) {
        va = ObjectAnimator.ofFloat(layoutParams, "height", 1f, measuredHeight.toFloat())
        va.interpolator = AccelerateInterpolator()
        visibility = View.VISIBLE
    } else {
        va = ObjectAnimator.ofFloat(layoutParams, "height", measuredHeight.toFloat(), 0f)
        va.interpolator = DecelerateInterpolator()
    }
    va.addListener(object : AnimatorListenerWrapper() {
        override fun onAnimationEnd(animation: Animator?) {
            if (expand) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                requestLayout()
            } else {
                visibility = View.GONE
            }
        }
    })
    va.duration = 600
    va.start()
}


