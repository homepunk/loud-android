package homepunk.github.com.presentation.feature.main;

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseActivity
import homepunk.github.com.presentation.core.ext.setupWithViewPager
import homepunk.github.com.presentation.databinding.ActivityMainBinding
import homepunk.github.com.presentation.feature.menu.MenuActivity


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var mMainViewModel: MainViewModel

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModels() {
        mMainViewModel = getViewModel(MainViewModel::class.java)
    }

    override fun init() {
        wLog("init")
        mDataBinding.run {
            viewModel = mMainViewModel
            onMenuClickListener = View.OnClickListener {
                startActivity(Intent(this@MainActivity, MenuActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle())
            }

            mainContent.viewPager.adapter = SimpleViewPagerAdapter(supportFragmentManager)
            mainContent.bottomNav.setupWithViewPager(mainContent.viewPager)
        }
    }

    override fun onResume() {
        super.onResume()
        mMainViewModel.init()

    }
}
