package homepunk.github.com.vinyl_underground.feature.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.View
import homepunk.github.com.vinyl_underground.base.BaseFragment

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
