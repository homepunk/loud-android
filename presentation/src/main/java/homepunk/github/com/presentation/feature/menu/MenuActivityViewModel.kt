package homepunk.github.com.presentation.feature.menu

import androidx.databinding.ObservableInt
import androidx.fragment.app.Fragment
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.menu.MenuModeModel
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.widget.tablayout.BubbleTabLayout
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
class MenuActivityViewModel @Inject constructor(var appDataFactory: AppDataFactory,
                                                var appModeInteractor: AppModeInteractor) : BaseViewModel() {

    var modeList = mutableListOf<MenuModeModel>()
    val menuAdapter = SimpleBindingRecyclerViewAdapter<MenuModel>(R.layout.layout_item_menu, BR.model)

    var menuItemFragmentList = arrayListOf<Fragment>()
    var menuItemFragmentIndex = ObservableInt(0)


    init {
        modeList = appDataFactory.getMenuModeList()
        menuAdapter.itemList = appDataFactory.getMenuList().apply {
            for (menuModel in this) {
                menuItemFragmentList.add(
                        when (menuModel) {
                            MenuModel.ABOUT -> Fragment()
                            MenuModel.COUNTRY -> CountryListFragment()
                            MenuModel.LANGUAGE -> LanguageListFragment()
                        })
            }
        }
        menuAdapter.onItemClickListener = object : OnItemClickListener<MenuModel> {
            override fun onClick(position: Int, item: MenuModel) {
                menuItemFragmentIndex.set(position)
            }
        }
    }

    var onMenuTabClickListener = object : BubbleTabLayout.OnMenuTabClickListener {
        override fun onTabClick(index: Int, item: BubbleTabLayout.TabItem) {
            val menuModel = modeList[index]
            appModeInteractor.changeAppMode(menuModel.appMode!!)
        }
    }
}