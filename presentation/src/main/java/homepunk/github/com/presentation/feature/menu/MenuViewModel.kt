package homepunk.github.com.presentation.feature.menu

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.menu.MenuModeModel
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.menu.country.ChangeLocationFragment
import homepunk.github.com.presentation.feature.menu.country.ChangeLocationViewModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryViewModel
import homepunk.github.com.presentation.feature.widget.menu.MenuLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.UnsupportedOperationException
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
class MenuViewModel @Inject constructor(var appDataFactory: AppDataFactory,
                                        var appModeInteractor: AppModeInteractor) : BaseViewModel() {

    var modeList = mutableListOf<MenuModeModel>()

    var menuItemList = ObservableArrayList<MenuLayout.MenuItem>()
    var menuItemFragmentList = arrayListOf<Fragment>()
    var userLocation = ObservableField<CountryViewModel>()
//    var onMenuTabClickListener = object : BubbleTabLayout.OnMenuTabClickListener {
//        override fun onTabClick(index: Int, item: BubbleTabLayout.TabItem) {
//            val menuModel = modeList[index]
//            appModeInteractor.changeAppMode(menuModel.appMode!!)
//        }
//    }

    init {
        modeList = appDataFactory.getModeList()
        menuItemList.addAll(
                appDataFactory.getModeMenuList().apply {
                    for (menuModel in this) {
                        menuItemFragmentList.add(
                                when (menuModel.actionId) {
                                    MenuModel.MAP.ordinal -> Fragment()
                                    MenuModel.COUNTRY.ordinal -> ChangeLocationFragment()
                                    MenuModel.CALENDAR.ordinal -> Fragment()
                                    else -> throw UnsupportedOperationException("NO MENU ITEM WITH THIS TYPE ${menuModel.actionId}")
                    })
                }})
        ChangeLocationViewModel.userLocationPublisher.subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { userLocation.set(it) }
    }
}