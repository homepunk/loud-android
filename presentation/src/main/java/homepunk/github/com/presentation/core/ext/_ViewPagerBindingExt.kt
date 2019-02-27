package homepunk.github.com.presentation.core.ext

import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter

/**Created by Homepunk on 21.01.2019. **/

@BindingAdapter(requireAll = true, value = [
    "adapter",
    "fragmentList"
])
fun ViewPager.setUpViewPager(adapter: SimpleViewPagerAdapter, fragmentList: List<Fragment>) {
    adapter.mFragmentList.addAll(fragmentList)
    this.adapter = adapter
}

@BindingAdapter("onPageChangeListener")
fun ViewPager.bindOnPageChangeListener(listener: ViewPager.OnPageChangeListener) {
    addOnPageChangeListener(listener)
}

@BindingAdapter("selectedItemPosition")
fun ViewPager.bindSelectedItemPosition(position: Int) {
    setCurrentItem(position, true)
}