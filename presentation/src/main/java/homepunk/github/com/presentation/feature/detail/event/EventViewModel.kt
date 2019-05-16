package homepunk.github.com.presentation.feature.detail.event

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.model.songkick.SongkickArtist
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.common.adapter.timeline.TimelineEventAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.detail.event.model.VenueModel
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**Created by Homepunk on 08.04.2019. **/
class EventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor) : BaseViewModel() {
    lateinit var eventModel: EventModel

    var timelineEventAdapter = TimelineEventAdapter<EventModel>()

    var performanceList = ObservableArrayList<SongkickArtist>()
//    var nearestEventList = ObservableArrayList<TimelineItem<TimelineModel>>()

    var venueModel = ObservableField<VenueModel>(VenueModel())

    fun init(model: ArrayList<EventModel>) {
        eventModel = model[0]
        setUpNearestEventsTimeline(model)

        compositeDisposable.add(eventInteractor.getEventDetails(eventModel.event.id)
                .doOnError { it.printStackTrace() }
//                .flatMapIterable { it.performance }
//                .filter { it.artist != null }
//                .sorted { o1, o2 ->  o1.billingIndex.compareTo(o2.billingIndex) }
//                .map { it.artist!! }
                .doOnNext { wLog(it.displayName) }
//                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    setUpLineUp(it)
                    setUpVenueInfo(it)
                })
    }

    private fun setUpLineUp(it: SongkickEvent) {
        it.performance
                .filter { it.artist != null }
                .forEach {
                    performanceList.add(it.artist!!)
                }
    }

    private fun setUpVenueInfo(it: SongkickEvent) {
        with(it.venue) {
            venueModel.set(VenueModel(
                    displayName,
                    lat,
                    lng,
                    phone,
                    website,
                    street,
                    city?.displayName))

        }
    }

    private fun setUpNearestEventsTimeline(eventList: ArrayList<EventModel>) {
        timelineEventAdapter.itemList = eventList.subList(1).toArrayList()
        timelineEventAdapter.onItemClickListener = object : OnItemClickListener<EventModel> {
            override fun onClick(position: Int, item: EventModel) {

            }
        }
    }

}

private fun <T> ObservableArrayList<T>.addAllFromIndex(startIndex: Int, source: java.util.ArrayList<T>) {
    addAll(source.subList(startIndex, source.size - 1))
}

private fun <T> ArrayList<T>.subList(fromIndex: Int): MutableList<T> {
    return if (fromIndex >= size) mutableListOf() else subList(fromIndex, size)
}
