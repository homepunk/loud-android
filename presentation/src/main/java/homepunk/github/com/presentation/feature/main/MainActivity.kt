package homepunk.github.com.presentation.feature.main;

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
        mDataBinding.pagerAdapter = SimpleViewPagerAdapter(supportFragmentManager)
        mDataBinding.viewModel = mMainViewModel
        mDataBinding.menuViewModel = mMainMenuViewModel
    }

    override fun onResume() {
        super.onResume()
        mMainViewModel.init()
    }
}
