package homepunk.github.com.presentation.feature.main.menu

import android.view.View
import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.feature.widget.tablayout.BubbleTabLayout
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
class MainMenuViewModel @Inject constructor(var appDataFactory: AppDataFactory) : BaseObservable() {
    @Inject
    lateinit var appModeInteractor: AppModeInteractor

    var isMenuOpened = ObservableBoolean(false)
    var title = ObservableField<String>()
    var menuList = mutableListOf<MenuModel>()
    var menuTabArray: Array<BubbleTabLayout.TabItem?>
    val menuAdapter = SimpleBindingRecyclerViewAdapter(R.layout.layout_item_menu, BR.title, arrayListOf("About", "Theme", "Color", "Contacts", "Country"))

    init {
        menuList.addAll(appDataFactory.getMenuList())
        menuTabArray = arrayOfNulls(menuList.size)
        menuList.forEachIndexed { i, model ->
            menuTabArray[i] = BubbleTabLayout.TabItem(model.iconResId, model.titleResId, model.colorResId)
        }
    }

    var onMenuTabClickListener = object : BubbleTabLayout.OnMenuTabClickListener {
        override fun onTabClick(index: Int, item: BubbleTabLayout.TabItem) {
            Timber.d("onClicked $index")
            val menuModel = menuList[index]
            appModeInteractor.changeAppMode(menuModel.appMode!!)
        }
    }

    var onMenuClickListener = View.OnClickListener {
        title.set(if (isMenuOpened.get())
            appDataFactory.getAppModeModel(appModeInteractor.currentAppMode).title
        else
            it.context.getString(R.string.label_menu))

        isMenuOpened.swap()
    }
}