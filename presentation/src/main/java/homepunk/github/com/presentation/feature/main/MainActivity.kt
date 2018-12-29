package homepunk.github.com.presentation.feature.main;

import android.view.View
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingActivity
import homepunk.github.com.presentation.core.wrapper.BottomSheetCallbackWrapper
import homepunk.github.com.presentation.databinding.ActivityMainBinding
import homepunk.github.com.presentation.feature.adapter.pager.ViewPagerAdapter
import homepunk.github.com.presentation.feature.main.home.HomeFragment
import homepunk.github.com.presentation.feature.main.recognition.CameraRecognitionFragment


class MainActivity : BaseBindingActivity<MainActivityViewModel, ActivityMainBinding>() {
    private lateinit var homeFragment: HomeFragment
    private lateinit var cameraFragment: CameraRecognitionFragment

    override fun getLayoutId() = R.layout.activity_main

    override fun createViewModel() = MainActivityViewModel(this)

    override fun init() {
        setUpViewPager()
        setUpBottomMenu()
    }

    private fun setUpBottomMenu() {
        val bottomMenu = mDataBinding.container.findViewById<LinearLayout>(R.id.bottom_menu_container)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomMenu)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallbackWrapper() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mDataBinding.pagerOverlay.alpha = slideOffset * 0.7f
            }
        })
    }


    private fun setUpViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        homeFragment = HomeFragment()
        val homeFragment2 = HomeFragment()
        val homeFragment3 = HomeFragment()
        cameraFragment = CameraRecognitionFragment()
        adapter.addFragment(homeFragment)
        adapter.addFragment(homeFragment2)
        adapter.addFragment(homeFragment3)
        mDataBinding.run {
            viewPager.adapter = adapter
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    navigation.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    navigation.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    navigation.onPageScrollStateChanged(state)
                }
            })
            viewPager.currentItem = 0
        }
    }
}
