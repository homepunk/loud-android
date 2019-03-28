package homepunk.github.com.presentation.feature.discover.event

import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import homepunk.github.com.presentation.feature.discover.event.model.UpcomingEventBindingParentModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DiscoverEventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor) : BaseViewModel() {

//    @Inject
//    lateinit var eventInteractor: SongkickEventInteractor

    var primaryEventsList = ObservableArrayList<EventModel>()
    var locationEventsList = ObservableArrayList<UpcomingEventBindingParentModel>()

    init {
        fetchUpcomingEventList()
    }

    fun fetchUpcomingEventList() {
        eventInteractor.getUpcomingEventList()
                .doOnNext { wLog("Location: ${it.first.city?.displayName}, events = ${it.second.size}") }
                .map {
                    val eventModels = arrayListOf<EventModel>()
                    it.second.forEach { eventModels.add(EventModel(it)) }
                    return@map Pair(it.first, eventModels)
                }
                .map { UpcomingEventBindingParentModel(it.first.city?.displayName, it.second) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { pair -> primaryEventsList.addAllToEmptyList(pair[1].eventList) }
                .subscribe { itemList -> locationEventsList.addAllToEmptyList(itemList) }
    }
}

