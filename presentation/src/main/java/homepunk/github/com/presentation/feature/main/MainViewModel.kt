package homepunk.github.com.presentation.feature.main

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.domain.model.internal.UserLocation
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

    var currentUserLocationPosition = MutableLiveData(-1)
    var userLocationListLiveData = MutableLiveData<List<UserLocation>>()

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

    var onLocationDropDownClickListener = object : OnDropDownClickListener<UserLocation> {
        override fun onClick(position: Int, item: UserLocation) {
            Timber.w("onClick $position, ${item.locationName}")
        }

        override fun onSelect(position: Int, newLocation: UserLocation) {
            Timber.w("onSelect $position, ${newLocation.locationName}")
            if (currentUserLocationPosition.value != position) {
                val currentLocation = userLocationListLiveData.value?.get(currentUserLocationPosition.value!!)!!
                userConfigurationInteractor.updateUserLocationList(listOf(
                        currentLocation.apply { isCurrent = false },
                        newLocation.apply { isCurrent = true }))
            }
        }
    }

    val isMenuOpenedLiveData = MutableLiveData<Boolean>()
    val onMenuClickListener = View.OnClickListener { isMenuOpenedLiveData.swap() }

    val isLocationOpenedLiveData = MutableLiveData<Boolean>()
    val onLocationClickListener = View.OnClickListener { isLocationOpenedLiveData.swap() }

    init {
        modeList = appDataFactory.getModeList()
        fragmentList.add(DiscoverHostFragment())
        fragmentList.add(EventTimelineFragment())
        fragmentList.add(Fragment())

        subscriptions.add(userConfigurationInteractor.getUserLocationList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    currentUserLocationPosition.value = it.indexOfFirst { it.isCurrent }
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
