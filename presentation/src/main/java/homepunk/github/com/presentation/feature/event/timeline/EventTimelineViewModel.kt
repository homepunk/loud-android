package homepunk.github.com.presentation.feature.event.timeline

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.ViewModel;
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
import homepunk.github.com.presentation.feature.event.home.model.LocationEventTimelineModel
import homepunk.github.com.presentation.feature.event.model.EventModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventTimelineViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                                 userLocationInteractor: UserLocationInteractor) : BaseViewModel() {
    var upcomingEventsList = ObservableArrayList<LocationEventTimelineModel>()
    var userLocationLiveData = userLocationInteractor.getCurrentUserLocation().toLiveData(BackpressureStrategy.LATEST)
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
