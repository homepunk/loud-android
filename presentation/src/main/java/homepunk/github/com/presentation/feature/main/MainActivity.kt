package homepunk.github.com.presentation.feature.main;

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseActivity
import homepunk.github.com.presentation.core.ext.setupWithViewPager
import homepunk.github.com.presentation.databinding.ActivityMainBinding
import homepunk.github.com.presentation.feature.menu.MenuActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var mMainViewModel: MainViewModel

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {
        wLog("init")
        mMainViewModel = getViewModel(MainViewModel::class.java)

        mDataBinding.run {
            viewModel = mMainViewModel
            onMenuClickListener = View.OnClickListener {
                startActivity(Intent(this@MainActivity, MenuActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
            }

            mainContent.viewPager.adapter = SimpleViewPagerAdapter(supportFragmentManager)
            mainContent.bottomNav.setupWithViewPager(mainContent.viewPager)
        }
    }
}
