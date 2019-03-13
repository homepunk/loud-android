package homepunk.github.com.presentation.feature.menu

import android.view.View
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseActivity
import homepunk.github.com.presentation.databinding.ActivityMenuBinding

class MenuActivity : BaseActivity<ActivityMenuBinding>() {
    lateinit var mMenuViewModel: MenuActivityViewModel

    override fun getLayoutId() = R.layout.activity_menu

    override fun initViewModels() {
        mMenuViewModel = getViewModel(MenuActivityViewModel::class.java)
    }

    override fun init() {
        mMenuViewModel.init()
        mDataBinding.run {
            menuViewModel = mMenuViewModel
            menuItemPagerAdapter = SimpleViewPagerAdapter(supportFragmentManager)

            onMenuClickListener = View.OnClickListener {
                finish()
            }
        }
    }

}
