package homepunk.github.com.presentation.common.data

import android.content.Context
import androidx.core.content.ContextCompat
import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.model.CountryModel
import homepunk.github.com.presentation.common.model.menu.MenuModeModel
import homepunk.github.com.presentation.common.model.menu.MenuModel
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.common.model.section.DiscoverSectionModel
import homepunk.github.com.presentation.feature.widget.filterlayout.FilterModel
import homepunk.github.com.presentation.feature.widget.menu.MenuItemView.MenuItemConfiguration
import homepunk.github.com.presentation.feature.widget.menu.MenuLayout
import javax.inject.Inject

class AppDataFactory @Inject constructor(val context: Context) {
    private var modeModelList= mutableListOf<AppModeModel>()
    private var librarySectionList = mutableListOf<DiscoverSectionModel>()

//    lateinit var modeTabArray: Array<BubbleTabLayout.TabItem>

    init {
        modeModelList.add(AppModeModel(AppMode.RELEASES, context.getString(R.string.title_menu_library), ContextCompat.getColor(context, R.color.modeLibraryColor), R.drawable.ic_mode_library, R.style.AppModeLibraryTheme))
        modeModelList.add(AppModeModel(AppMode.EVENTS, context.getString(R.string.title_menu_events), ContextCompat.getColor(context, R.color.modeEventsColor), R.drawable.ic_mode_events, R.style.AppModeEventTheme))
//        modeModelList.add(AppModeModel(AppMode.GEAR, context.getString(R.string.title_menu_gear), ContextCompat.getColor(context, R.color.modeGearColor), R.drawable.ic_mode_gear, R.style.AppModeGearTheme))


        librarySectionList.add(DiscoverSectionModel(0, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_RAP, context.resources.getStringArray(R.array.genres)[0]))
        librarySectionList.add(DiscoverSectionModel(1, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ROCK, context.resources.getStringArray(R.array.genres)[1]))
        librarySectionList.add(DiscoverSectionModel(2, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_REGGAE, context.resources.getStringArray(R.array.genres)[2]))
        librarySectionList.add(DiscoverSectionModel(3, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ELECTRONIC, context.resources.getStringArray(R.array.genres)[3]))
        librarySectionList.add(DiscoverSectionModel(4, Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE, context.resources.getStringArray(R.array.genres)[4]))
    }


    fun getLibraryDiscoverSectionList(): List<DiscoverSectionModel> {
        return librarySectionList
    }

    fun getAppModeModelList(): List<AppModeModel> {
        return modeModelList
    }

    fun getFilters(): Array<FilterModel> {
        return arrayOf(FilterModel("ALL", true), FilterModel("#Rap"), FilterModel("#Rock"), FilterModel("#Techno"), FilterModel("#Experimental"))
    }

    fun getAppModeModel(mode: AppMode) = modeModelList.find { it.mode == mode }!!

    fun getModeList(): MutableList<MenuModeModel> {
        val menuList = mutableListOf<MenuModeModel>()
        menuList.add(MenuModeModel(MenuModeModel.TYPE.LIBRARY, R.color.modeLibraryColor, R.string.title_menu_library, R.drawable.ic_mode_library, AppMode.RELEASES))
        menuList.add(MenuModeModel(MenuModeModel.TYPE.EVENTS, R.color.modeEventsColor, R.string.title_menu_events, R.drawable.ic_mode_events, AppMode.EVENTS))
//        menuList.add(MenuModeModel(MenuModeModel.TYPE.GEAR, R.color.modeGearColor, R.string.title_menu_gear, R.drawable.ic_mode_gear, AppMode.GEAR))
        return menuList
    }

//    fun getModeMenuList() = MenuModel.values().asList()

    fun getModeMenuList() : List<MenuLayout.MenuItem> {
        return arrayListOf(
                MenuLayout.MenuItem(MenuModel.COUNTRY.ordinal, R.string.menu_item_location),
                MenuLayout.MenuItem(MenuModel.CALENDAR.ordinal, R.string.menu_item_calendar),
                MenuLayout.MenuItem(MenuModel.MAP.ordinal, R.string.menu_item_map)
        )
    }

    fun getCountryModelList(): List<CountryModel> {
        val dataList = mutableListOf<CountryModel>()
        dataList.add(CountryModel.UA)
        dataList.add(CountryModel.FR)
        dataList.add(CountryModel.DE)
        dataList.add(CountryModel.US)
        return dataList
    }
}