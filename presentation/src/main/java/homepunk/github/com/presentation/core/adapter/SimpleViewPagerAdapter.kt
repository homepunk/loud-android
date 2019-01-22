package homepunk.github.com.presentation.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import timber.log.Timber

/**Created by Homepunk on 11.01.2019. **/
class SimpleViewPagerAdapter(var supportFragmentManager: FragmentManager?) : FragmentPagerAdapter(supportFragmentManager) {
    val mFragmentList = arrayListOf<Fragment>()

    fun reset() {
        for (i in 0 until mFragmentList.size) {
            supportFragmentManager?.beginTransaction()?.remove(mFragmentList[i])?.commit()
        }
        mFragmentList.clear()
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        Timber.w("getCount() = ${mFragmentList.size}")
        return mFragmentList.size
    }
}
