package homepunk.github.com.presentation.feature.event.home

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
import homepunk.github.com.presentation.feature.event.model.EventModel
import homepunk.github.com.presentation.feature.event.home.model.LocationEventTimelineModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                             userLocationInteractor: UserLocationInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var popularEventsList = ObservableArrayList<EventModel>()

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

    val onEventClickListener = object : OnItemClickListener<EventModel> {
        override fun onClick(position: Int, item: EventModel) {
            val maxIndex = position + 6
            onEventClickLiveData.value = popularEventsList.subList(position, if (maxIndex < popularEventsList.size) maxIndex else popularEventsList.size).toArrayList()
        }
    }

    var onEventClickLiveData = SingleLiveData<ArrayList<EventModel>>()

    fun updateUpcomingEventList(userLocation: UserLocation) {
        compositeDisposable.add(eventInteractor.getUpcomingEventsForUserLocation(userLocation)
                .map { it.second.map { event -> EventModel(event) } }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (primaryEventsList.isEmpty()) {
                        primaryEventsList.addAllToEmptyList(it.reversed())
                        popularEventsList.addAllToEmptyList(it)                    }
                }
                .toList()
                .doOnError { it.printStackTrace() }
                .subscribe())
    }
}


