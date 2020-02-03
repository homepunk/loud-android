package homepunk.github.com.presentation.feature.main

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.domain.model.internal.CityLocation
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.menu.MenuModeModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.event.timeline.EventTimelineFragment
import homepunk.github.com.presentation.feature.releases.DiscoverHostFragment
import homepunk.github.com.presentation.feature.widget.dropdownlistview.OnDropDownClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(var appDataFactory: AppDataFactory,
                                        var appModeInteractor: AppModeInteractor,
                                        var userConfigurationInteractor: UserConfigurationInteractor) : BaseViewModel() {

    var fragmentList = arrayListOf<Fragment>()
    var modeList = mutableListOf<MenuModeModel>()
    var filers = appDataFactory.getFilters()

    private var currentUserLocationIndex = -1
    var userLocationListLiveData = MutableLiveData<List<CityLocation>>()

    var onModeClickListener = View.OnClickListener { v ->
        v?.let {
            when (it.id) {
                R.id.btn_event -> {
                    appModeInteractor.changeAppMode(AppMode.EVENTS)
                }
                R.id.btn_releases -> {
                    appModeInteractor.changeAppMode(AppMode.RELEASES)
                }
            }
        }
    }

    var onLocationDropDownClickListener = object : OnDropDownClickListener<CityLocation> {
        override fun onClick(position: Int, item: CityLocation) {
            Timber.w("onClick $position, ${item.locationName}")
        }

        override fun onSelect(position: Int, newLocation: CityLocation) {
            Timber.w("onSelect $position, ${newLocation.locationName}")
            if (currentUserLocationIndex != position) {
                val currentLocation = userLocationListLiveData.value?.get(currentUserLocationIndex)!!
                userConfigurationInteractor.updateUserLocationList(listOf(
                        currentLocation.apply { isCurrent = false },
                        newLocation.apply { isCurrent = true }))
            }
        }
    }

    val isCountryChangeMenuOpenedLiveData = MutableLiveData<Boolean>()
    val onCountryChangeClickListener = View.OnClickListener { isCountryChangeMenuOpenedLiveData.swap() }

    init {
        modeList = appDataFactory.getModeList()
        fragmentList.add(DiscoverHostFragment())
        fragmentList.add(EventTimelineFragment())
        fragmentList.add(Fragment())

        subscriptions.add(userConfigurationInteractor.getUserLocationList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currentUserLocationIndex = it.indexOfFirst { it.isCurrent }
                    userLocationListLiveData.value = it
                }
        )
    }
}

fun MutableLiveData<Boolean>.swap() {
    value = if (value != null) !value!! else true
}


fun MutableLiveData<Boolean>.swapIfTrue() {
    value = if (value != null && value == true) !value!! else false
}
