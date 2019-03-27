package homepunk.github.com.presentation.feature.discover.event

import android.annotation.SuppressLint
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.discover.event.model.EventLocationModel
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class DiscoverEventViewModel @Inject constructor()
    : BaseViewModel() {

    @Inject
    lateinit var eventInteractor: SongkickEventInteractor

    val locatonEventsAdapter: SimpleBindingRecyclerViewAdapter<EventLocationModel> = SimpleBindingRecyclerViewAdapter(R.layout.layout_item_parent_location_children_events, BR.parentModel)
    val primaryEventAdapter: SimpleBindingRecyclerViewAdapter<EventModel> = SimpleBindingRecyclerViewAdapter(R.layout.layout_item_primary_event, BR.model)

    override fun init() {
    }

    @SuppressLint("CheckResult")
    fun fetchUpcomingEventList() {
        eventInteractor.getUpcomingEventList()
                .doOnNext { wLog("Location: ${it.first.city?.displayName}, events = ${it.second.size}") }
                .map {
                    val eventModels = arrayListOf<EventModel>()
                    it.second.forEach { eventModels.add(EventModel(it)) }
                    return@map Pair(it.first, eventModels)
                }
                .map { EventLocationModel(it.first.city?.displayName, it.second) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess { pair -> primaryEventAdapter.itemList = pair[1].getEvents()}
                .subscribe { itemList -> locatonEventsAdapter.itemList = itemList }
    }
}
