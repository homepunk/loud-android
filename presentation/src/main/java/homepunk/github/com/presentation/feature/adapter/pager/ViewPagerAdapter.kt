package homepunk.github.com.presentation.feature.adapter.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supportFragmentManager: FragmentManager?) : FragmentPagerAdapter(supportFragmentManager) {
    private val mFragmentList = arrayListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }


    override fun getCount(): Int {
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment) {
        mFragmentList.add(fragment)
    }
}
