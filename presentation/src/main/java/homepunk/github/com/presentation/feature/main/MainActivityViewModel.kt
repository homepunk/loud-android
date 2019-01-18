package homepunk.github.com.presentation.feature.main

import androidx.databinding.ObservableField
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    @Inject lateinit var appDataFactory: AppDataFactory

    var currentAppModeModel = ObservableField<AppModeModel>()
    get() {
        field.set(appDataFactory.getAppModeModelList()[0])
        return field
    }

    var menuList = listOf<MenuModel>()
        get() {
            field = appDataFactory.getMenuList()
            return field
        }
}

