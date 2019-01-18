package homepunk.github.com.presentation.common.data

import android.content.Context
import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.common.model.section.DiscoverSectionModel
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel
import javax.inject.Inject

class AppDataFactory @Inject constructor(val context: Context) {
    fun getDiscoverLibrarySectionList(): List<DiscoverSectionModel> {
        val dataList = mutableListOf<DiscoverSectionModel>()
        dataList.add(DiscoverSectionModel(0, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_RAP, context.resources.getStringArray(R.array.genres)[0]))
        dataList.add(DiscoverSectionModel(1, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ROCK, context.resources.getStringArray(R.array.genres)[1]))
        dataList.add(DiscoverSectionModel(2, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_REGGAE, context.resources.getStringArray(R.array.genres)[2]))
        dataList.add(DiscoverSectionModel(3, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ELECTRONIC, context.resources.getStringArray(R.array.genres)[3]))
        dataList.add(DiscoverSectionModel(4, Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE, context.resources.getStringArray(R.array.genres)[4]))
        return dataList
    }

    fun getAppModeModelList(): List<AppModeModel> {
        val dataList = mutableListOf<AppModeModel>()
        dataList.add(AppModeModel(AppModeModel.Mode.LIBRARY, R.string.title_menu_library, R.color.modeLibraryColor, R.drawable.ic_mode_library))
        dataList.add(AppModeModel(AppModeModel.Mode.EVENT, R.string.title_menu_events, R.color.modeEventsColor, R.drawable.ic_mode_events))
        dataList.add(AppModeModel(AppModeModel.Mode.GEAR, R.string.title_menu_gear, R.color.modeGearColor, R.drawable.ic_mode_gear))
        return dataList
    }

    fun getMenuList(): List<MenuModel> {
        val menuList = mutableListOf<MenuModel>()
        menuList.add(MenuModel(MenuModel.TYPE.LIBRARY, R.color.modeLibraryColor, R.string.title_menu_library, R.drawable.ic_mode_library))
        menuList.add(MenuModel(MenuModel.TYPE.EVENTS, R.color.modeEventsColor, R.string.title_menu_events, R.drawable.ic_mode_events))
        menuList.add(MenuModel(MenuModel.TYPE.GEAR, R.color.modeGearColor, R.string.title_menu_gear, R.drawable.ic_mode_gear))
        menuList.add(MenuModel(MenuModel.TYPE.SETTINGS, R.color.menuColorSettings, R.string.title_menu_settings, R.drawable.ic_settings))
        return menuList
    }

    fun getCountryModelList(): List<CountryModel> {
        val dataList = mutableListOf<CountryModel>()
        dataList.add(CountryModel.UA)
        dataList.add(CountryModel.FR)
        dataList.add(CountryModel.RU)
        return dataList
    }
}