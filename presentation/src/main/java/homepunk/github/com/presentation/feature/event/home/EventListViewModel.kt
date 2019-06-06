package homepunk.github.com.presentation.feature.event.home

import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserLocationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.common.data.SingleLiveData
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.ext.toLiveData
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.event.model.EventModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                             userLocationInteractor: UserLocationInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var popularEventsList = ObservableArrayList<EventModel>()

    var userLocationLiveData = userLocationInteractor.getCurrentUserLocation().toLiveData(BackpressureStrategy.LATEST)

    var onEventClickLiveData = SingleLiveData<ArrayList<EventModel>>()
    val onEventClickListener = object : OnItemClickListener<EventModel> {
        override fun onClick(position: Int, item: EventModel) {
            val maxIndex = position + 6
            onEventClickLiveData.value = popularEventsList.subList(position, if (maxIndex < popularEventsList.size) maxIndex else popularEventsList.size).toArrayList()
        }
    }

    fun updateUpcomingEventList(userLocation: UserLocation) {
        compositeDisposable.add(eventInteractor.getUpcomingEventsForUserLocation(userLocation)
                .subscribeOn(Schedulers.computation())
                .map { it.second.map { event -> EventModel(event) } }
                .doOnError { it.printStackTrace() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    primaryEventsList.addAllToEmptyList(it)
                    popularEventsList.addAllToEmptyList(it)
                })
    }

    override fun onCleared() {
        super.onCleared()
//        primaryEventsList.clear()
//        popularEventsList.clear()
    }
}


