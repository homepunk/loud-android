package homepunk.github.com.presentation.feature.event.home

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.interactor.UserConfigurationInteractor
import homepunk.github.com.presentation.common.data.SingleLiveData
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.event.model.EventModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@SuppressLint("CheckResult")
class EventListViewModel @Inject constructor(private var eventInteractor: SongkickEventInteractor,
                                             var userConfigurationInteractor: UserConfigurationInteractor) : BaseViewModel() {

    var primaryEventsList = ObservableArrayList<EventModel>()
    var popularEventsList = ObservableArrayList<EventModel>()

    var onEventClickLiveData = SingleLiveData<ArrayList<EventModel>>()
    val onEventClickListener = object : OnItemClickListener<EventModel> {
        override fun onClick(position: Int, item: EventModel) {
            val maxIndex = position + 6
            onEventClickLiveData.value = popularEventsList.subList(position, if (maxIndex < popularEventsList.size) maxIndex else popularEventsList.size).toArrayList()
        }
    }

    init {
        subscriptions.add(
                userConfigurationInteractor
                        .getUserCurrentLocation()
                        .flatMap(eventInteractor::getUpcomingEventsForUserLocation)
                        .map { it.map { event -> EventModel(event) } }
                        .doOnError { it.printStackTrace() }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            primaryEventsList.addAllToEmptyList(it)
                            popularEventsList.addAllToEmptyList(it)
                        })
    }
}


