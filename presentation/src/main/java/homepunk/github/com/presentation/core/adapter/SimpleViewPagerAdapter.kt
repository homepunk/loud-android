package homepunk.github.com.presentation.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**Created by Homepunk on 11.01.2019. **/
class SimpleViewPagerAdapter(var supportFragmentManager: FragmentManager?) : FragmentPagerAdapter(supportFragmentManager) {
    val mFragmentList = arrayListOf<Fragment>()
    val mTitleArray = arrayOf("Discover", "Collection", "Map")

    override fun getItem(position: Int) = mFragmentList[position]

    override fun getCount() = mFragmentList.size

    override fun getPageTitle(position: Int): CharSequence? = if (position < mTitleArray.size) mTitleArray[position] else null
}
