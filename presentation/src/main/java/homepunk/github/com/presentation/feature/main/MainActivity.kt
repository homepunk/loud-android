package homepunk.github.com.presentation.feature.main;

import android.content.res.Resources
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseBindingActivity
import homepunk.github.com.presentation.databinding.ActivityMainBinding
import homepunk.github.com.presentation.feature.main.discover.DiscoverFragment
import homepunk.github.com.presentation.feature.main.menu.MainMenuViewModel
import homepunk.github.com.presentation.feature.mode.event.EventFragment
import homepunk.github.com.presentation.feature.recognition.CameraRecognitionFragment
import javax.inject.Inject


class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    @Inject
    lateinit var mMainMenuViewModel: MainMenuViewModel

    private lateinit var mMainViewModel: MainActivityViewModel

    private lateinit var discoverFragment: DiscoverFragment
    private lateinit var eventFragment: EventFragment
    private lateinit var cameraFragment: CameraRecognitionFragment

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModels() {
        mMainViewModel = getViewModel(MainActivityViewModel::class.java)
    }

    override fun init() {
        wLog("init")
        mDataBinding.viewModel = mMainViewModel
        mDataBinding.menuViewModel = mMainMenuViewModel
    }

    override fun onPreInfalte() {
        mMainViewModel.setUpAppMode()
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        mMainViewModel.currentTheme?.run {
            theme.applyStyle(this, true)
        }
        return theme
    }
    override fun onResume() {
        super.onResume()
        wLog("onResume")
        mMainViewModel.themeLiveData.observe(this, Observer {
            wLog("observe $it")
            try {
                if (mMainViewModel.currentTheme != it) {
                    recreate()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val adapter = SimpleViewPagerAdapter(supportFragmentManager)
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
//                    navigation.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
//                    navigation.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
//                    navigation.onPageScrollStateChanged(state)
                }
            })
        }
    }
}
