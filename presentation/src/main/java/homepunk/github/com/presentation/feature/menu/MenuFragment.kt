package homepunk.github.com.presentation.feature.menu

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.core.ext.setupWithViewPager
import homepunk.github.com.presentation.databinding.FragmentMenuBinding


class MenuFragment : BaseFragment<FragmentMenuBinding>() {
    private lateinit var mMenuViewModel: MenuViewModel

    override var layoutId = R.layout.fragment_menu

    override fun init() {
        mMenuViewModel = getViewModel(MenuViewModel::class.java)
        mDataBinding.run {
            menuViewModel = mMenuViewModel

            vpMenuItemDetail.adapter = SimpleViewPagerAdapter(childFragmentManager)
//            rvModeMenu.adapter = SimpleBindingRecyclerAdapter<MenuModel>(R.layout.layout_item_menu, BR.model)
//            rvModeMenu.setupWithViewPager(vpMenuItemDetail)

           /* onMenuClickListener = View.OnClickListener {
                onBackPressed()
            }*/
        }
    }
}
