package homepunk.github.com.presentation.feature.event.timeline

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.common.data.SingleLiveData
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.event.model.EventModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CheckResult")
class EventTimelineViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor,
                                                 userConfigurationInteractor: UserConfigurationInteractor) : BaseViewModel() {
    var upcomingEventsList = MutableLiveData<List<EventModel>>()

    var onEventClickLiveData = SingleLiveData<ArrayList<EventModel>>()
    var onEventClickListener = object : OnItemClickListener<EventModel> {
        override fun onClick(position: Int, item: EventModel) {
            upcomingEventsList.run {
                val maxIndex = position + 6
                onEventClickLiveData.value = value!!.subList(position, if (maxIndex < value!!.size) maxIndex else value!!.size).toArrayList()
            }
        }
    }

    init {
        subscriptions.add(
                userConfigurationInteractor.getUserCurrentLocation()
                        .doOnNext { Timber.w("RECEIVE NEXT CURRENT LOCATION= ${it.locationName}") }
                        .flatMap { getUpcomingEventList(it) }
                        .subscribe { upcomingEventsList.value = it })
    }

    private fun getUpcomingEventList(userLocation: UserLocation) =
            eventInteractor.getUpcomingEventsForUserLocation(userLocation)
                    .subscribeOn(Schedulers.computation())
                    .map {
                        it
                                .map { EventModel(it) }
                                .filter { it.event.start != null && it.event.start!!.date != null }
                    }
                    .doOnError { it.printStackTrace() }
                    .observeOn(AndroidSchedulers.mainThread())

}
