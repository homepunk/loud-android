package homepunk.github.com.presentation.feature.discover.event

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableInt
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserLocationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.common.data.SingleLiveData
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.removeWhen
import homepunk.github.com.presentation.core.ext.toLiveData
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.LocationEventBindingParentModel
import homepunk.github.com.presentation.feature.discover.event.model.UpcomingEventBindingChildModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DiscoverEventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                                 var userLocationInteractor: UserLocationInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var locationEventsList = ObservableArrayList<LocationEventBindingParentModel>()

    var userLocationLiveData = userLocationInteractor.getCurrentUserLocation().toLiveData(BackpressureStrategy.LATEST)

    var parentFocusIndex =  ObservableInt(-1)
    var onParentClickListener = object : OnItemClickListener<LocationEventBindingParentModel> {
        override fun onClick(position: Int, parent: LocationEventBindingParentModel) {
            if (parent.isParentExpanded.get()) {
                parentFocusIndex.set(position)
            } else {
                parentFocusIndex.set(-1)
            }
        }
    }

    var onChildClickEventLiveData = SingleLiveData<UpcomingEventBindingChildModel>()
    var onChildClickListener = object : OnItemClickListener<UpcomingEventBindingChildModel> {
        override fun onClick(position: Int, item: UpcomingEventBindingChildModel) {
            onChildClickEventLiveData.value = item
        }
    }

    fun updateUpcomingEventList(userLocation: UserLocation) {
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

