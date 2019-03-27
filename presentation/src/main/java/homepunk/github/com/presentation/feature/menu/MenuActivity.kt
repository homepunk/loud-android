package homepunk.github.com.presentation.feature.menu

import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.view.Window
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseActivity
import homepunk.github.com.presentation.core.ext.setupWithViewPager
import homepunk.github.com.presentation.databinding.ActivityMenuBinding



class MenuActivity : BaseActivity<ActivityMenuBinding>() {
    private lateinit var mMenuViewModel: MenuViewModel

    override fun getLayoutId() = R.layout.activity_menu

    override fun initViewModels() {
        mMenuViewModel = getViewModel(MenuViewModel::class.java)
    }

    override fun init() {
        mMenuViewModel.init()
        mDataBinding.run {
            menuViewModel = mMenuViewModel

            vpMenuItemDetail.adapter = SimpleViewPagerAdapter(supportFragmentManager)
            rvModeMenu.adapter = SimpleBindingRecyclerViewAdapter<MenuModel>(R.layout.layout_item_menu, BR.model)
            rvModeMenu.setupWithViewPager(vpMenuItemDetail)

            onMenuClickListener = View.OnClickListener {
                onBackPressed()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.enterTransition = Slide(Gravity.BOTTOM)
        window.exitTransition = Slide(Gravity.TOP)
        super.onCreate(savedInstanceState)
    }

}
