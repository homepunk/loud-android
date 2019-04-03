package homepunk.github.com.presentation.feature.discover.event

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserLocationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.removeWhen
import homepunk.github.com.presentation.core.ext.toLiveData
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.LocationEventBindingParentModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoverEventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                                 var userLocationInteractor: UserLocationInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var locationEventsList = ObservableArrayList<LocationEventBindingParentModel>()

    var userLocationLiveData = userLocationInteractor.getCurrentUserLocation().toLiveData(BackpressureStrategy.LATEST)

    fun fetchUpcomingEventList(userLocation: UserLocation) {
        compositeDisposable.add(eventInteractor.getUpcomingEventsForUserLocation(userLocation)
                .map { LocationEventBindingParentModel(it.first.city?.displayName, it.second.map { event -> EventModel(event) }) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (primaryEventsList.isEmpty()) {
                        primaryEventsList.addAllToEmptyList(it.eventList)
                    }
                }
                .toList()
                .doOnError { it.printStackTrace() }
                .subscribe { parentList ->
                    updateParentLocationList(parentList)
                })
    }

    private fun updateParentLocationList(newList: MutableList<LocationEventBindingParentModel>) {
        with(locationEventsList.listIterator()) {
            while (hasNext()) {
                val sourceItem = next()
                if (!newList.removeWhen { it.getLocationName() == sourceItem.getLocationName() }) {
                    remove()
                }
            }
        }
        locationEventsList.addAll(newList)
    }

}

