package homepunk.github.com.presentation.feature.discover.event

import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserLocationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.common.data.SingleLiveData
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.removeWhen
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.ext.toLiveData
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.LocationEventTimelineModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                             userLocationInteractor: UserLocationInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var upcomingEventsList = ObservableArrayList<LocationEventTimelineModel>()

    var userLocationLiveData = userLocationInteractor.getCurrentUserLocation().toLiveData(BackpressureStrategy.LATEST)

//    var parentFocusIndex = ObservableInt(-1)
//    var onParentClickListener = object : OnItemClickListener<LocationEventTimelineModel> {
//        override fun onClick(position: Int, parent: LocationEventTimelineModel) {
//            if (parent.isParentExpanded.get()) {
//                parentFocusIndex.set(position)
//            } else {
//                parentFocusIndex.set(-1)
//            }
//        }
//    }

    var onEventClickLiveData = SingleLiveData<ArrayList<EventModel>>()

    fun updateUpcomingEventList(userLocation: UserLocation) {
        compositeDisposable.add(eventInteractor.getUpcomingEventsForUserLocation(userLocation)
                .map { Pair(it.first.city?.displayName!!, it.second.map { event -> EventModel(event) }) }
                .map {
                    LocationEventTimelineModel(it.first, it.second,
                            object : OnItemClickListener<EventModel> {
                                override fun onClick(position: Int, item: EventModel) {
                                    it.second.run {
                                        val maxIndex = position + 6
                                        onEventClickLiveData.value = subList(position, if (maxIndex < size) maxIndex else size).toArrayList()
                                    }
                                }
                            })
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (primaryEventsList.isEmpty()) {
                        primaryEventsList.addAllToEmptyList(it.eventList)
                    }
                }
                .toList()
                .doOnError { it.printStackTrace() }
                .subscribe { parentList ->
                    updateUpcomingEventList(parentList)
                })
    }

    private fun updateUpcomingEventList(newList: MutableList<LocationEventTimelineModel>) {
        with(upcomingEventsList.listIterator()) {
            while (hasNext()) {
                val sourceItem = next()
                if (!newList.removeWhen { it.locationName == sourceItem.locationName }) {
                    remove()
                }
            }
        }

        upcomingEventsList.addAll(newList)
    }
}


