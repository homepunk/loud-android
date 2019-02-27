package homepunk.github.com.presentation.feature.main;

import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseActivity
import homepunk.github.com.presentation.databinding.ActivityMainBinding
import homepunk.github.com.presentation.feature.main.menu.MainMenuViewModel
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding>() {
    @Inject lateinit var mMainMenuViewModel: MainMenuViewModel
    private lateinit var mMainViewModel: MainActivityViewModel

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModels() {
        mMainViewModel = getViewModel(MainActivityViewModel::class.java)
    }

    override fun init() {
        wLog("init")
        mDataBinding.run {
            menuViewModel = mMainMenuViewModel
            viewModel = mMainViewModel

            mMainMenuViewModel.mainTitle.set(mAppModeViewModel.currentAppModeModelLiveData.value!!.title)
            pagerAdapter = SimpleViewPagerAdapter(supportFragmentManager).apply {
                mTitleList = mutableListOf("Discover", "Collection", "Map")
            }
            menuItemPagerAdapter = SimpleViewPagerAdapter(supportFragmentManager)
            mainContent.bottomNav.setOnNavigationItemSelectedListener listener@{
                when (it.itemId) {
                    R.id.action_discover -> mainContent.viewPager.currentItem = 0
                    R.id.action_favorites -> mainContent.viewPager.currentItem = 1
                    R.id.action_map -> mainContent.viewPager.currentItem = 2
                }
                return@listener true
            }
            mainContent.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                var prevMenuItem: MenuItem? = null
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    prevMenuItem?.isChecked = false
                    prevMenuItem = mainContent.bottomNav.menu.getItem(position).apply { isChecked = true }
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        mMainViewModel.init()

    }
}
