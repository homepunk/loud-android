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
    @Inject
    lateinit var mMainMenuViewModel: MainMenuViewModel
    private lateinit var mMainViewModel: MainActivityViewModel

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModels() {
        mMainViewModel = getViewModel(MainActivityViewModel::class.java)
    }

    override fun init() {
        wLog("init")
        mDataBinding.run {
            viewModel = mMainViewModel
            menuViewModel = mMainMenuViewModel

            pagerAdapter = SimpleViewPagerAdapter(supportFragmentManager)
            bottomNav.setOnNavigationItemSelectedListener listener@{
                when (it.itemId) {
                    R.id.action_discover -> viewPager.currentItem = 0
                    R.id.action_favorites -> viewPager.currentItem = 1
                    R.id.action_map -> viewPager.currentItem = 2
                }
                return@listener true
            }
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                var prevMenuItem: MenuItem? = null
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                }

                override fun onPageSelected(position: Int) {
                    prevMenuItem?.isChecked = false
                    prevMenuItem = bottomNav.menu.getItem(position).apply { isChecked = true }
                }
            })
            bottomNavLayout.setTitleArray(arrayOf("RAP", "ROCK", "DUB", "REGGAE", "NOISE"))
        }
    }

    override fun onResume() {
        super.onResume()
        mMainViewModel.init()
    }
}
