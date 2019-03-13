package homepunk.github.com.presentation.core.ext

import android.view.MenuItem
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter

/**Created by Homepunk on 21.01.2019. **/

@BindingAdapter(requireAll = false, value = [
    "adapter",
    "fragmentList"
])
fun ViewPager.setUpViewPager(adapter: SimpleViewPagerAdapter?, fragmentList: List<Fragment>) {
    if (adapter == null) {
        (getAdapter() as? SimpleViewPagerAdapter)?.let {
            it.mFragmentList.addAll(fragmentList)
            it.notifyDataSetChanged()
        }
    } else {
        adapter.mFragmentList.addAll(fragmentList)
        setAdapter(adapter)
    }
}

@BindingAdapter("onPageChangeListener")
fun ViewPager.bindOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
    addOnPageChangeListener(listener)
}

@BindingAdapter("selectedItemPosition")
fun ViewPager.bindSelectedItemPosition(position: Int) {
    setCurrentItem(position, true)
}


fun BottomNavigationView.setupWithViewPager(viewPager: ViewPager) {
    viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        var prevMenuItem: MenuItem? = null
        override fun onPageScrollStateChanged(state: Int) {

        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        }

        override fun onPageSelected(position: Int) {
            prevMenuItem?.isChecked = false
            prevMenuItem = menu.getItem(position).apply { isChecked = true }
        }
    })

    if (viewPager.adapter == null) {
        throw UnsupportedOperationException("Please specify adapter to the view pager before calling setupWithViewPager")
    }
    (viewPager.adapter as? SimpleViewPagerAdapter)?.let { adapter ->
        for (i in 0 until adapter.count) {
            menu.getItem(i)?.let {
                adapter.mTitleList.add(it.title.toString())
            }
        }

    }
    setOnNavigationItemSelectedListener listener@{
        viewPager.currentItem = it.order
        return@listener true
    }

}