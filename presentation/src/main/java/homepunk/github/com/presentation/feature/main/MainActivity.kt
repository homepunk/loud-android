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
import homepunk.github.com.presentation.feature.main.discover.DiscoverFragment
import homepunk.github.com.presentation.feature.main.event.EventFragment
import homepunk.github.com.presentation.feature.recognition.CameraRecognitionFragment


class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private lateinit var mMainViewModel: MainActivityViewModel

    private lateinit var discoverFragment: DiscoverFragment
    private lateinit var eventFragment: EventFragment
    private lateinit var cameraFragment: CameraRecognitionFragment

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModels() {
        mMainViewModel = MainActivityViewModel(this)
    }

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
        discoverFragment = DiscoverFragment()
        eventFragment = EventFragment()
        val homeFragment3 = DiscoverFragment()
        cameraFragment = CameraRecognitionFragment()
        adapter.addFragment(discoverFragment)
        adapter.addFragment(eventFragment)
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
        }
    }
}
