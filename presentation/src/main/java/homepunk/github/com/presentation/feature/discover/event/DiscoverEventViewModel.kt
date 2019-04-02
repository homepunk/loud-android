package homepunk.github.com.presentation.feature.discover.event

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserLocationSettingsInteractor
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.UpcomingEventBindingParentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoverEventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                                 var userLocationSettingsInteractor: UserLocationSettingsInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var locationEventsList = ObservableArrayList<UpcomingEventBindingParentModel>()

    init {
        fetchUpcomingEventList()
        userLocationSettingsInteractor.getCurrentUserLocationSettings().subscribe {
            wLog("SETTINGS UPDATE: ${it.currentUserCountryName}, CITY COUNT ${it.currentUserLocationList.size}")
        }
    }


    @SuppressLint("CheckResult")
    fun fetchUpcomingEventList() {
        Timber.w("fetchUpcomingEventList")
        eventInteractor.getUpcomingEventsFromUserList()
                .doOnNext { wLog("Location: ${it.first.city?.displayName}, events = ${it.second.size}") }
                .map { Pair(it.first, it.second.map { event -> EventModel(event) }) }
                .map { UpcomingEventBindingParentModel(it.first.city?.displayName, it.second) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { if (primaryEventsList.isEmpty()) {
                    primaryEventsList.addAllToEmptyList(it.eventList)
                }}
                .doOnError { it.printStackTrace() }
                .subscribe { parent -> locationEventsList.add(parent) }
    }
}

