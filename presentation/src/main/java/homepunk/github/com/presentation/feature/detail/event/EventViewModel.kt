package homepunk.github.com.presentation.feature.detail.event

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.model.songkick.SongkickArtist
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.common.adapter.timeline.TimelineEventAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.subList
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.detail.event.model.VenueModel
import homepunk.github.com.presentation.feature.discover.event.model.EventModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**Created by Homepunk on 08.04.2019. **/
class EventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor) : BaseViewModel() {
    lateinit var eventModel: EventModel

    var youtubeQueryLiveData = MutableLiveData<String>()

    var timelineEventAdapter = TimelineEventAdapter()
    var performanceList = ObservableArrayList<SongkickArtist>()

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    setUpLineUp(it)
//                    getArtistsYoutubeVideos(it)
                    setUpVenueInfo(it)
                })
    }

    private fun setUpLineUp(it: SongkickEvent) {
        Observable.fromIterable(it.performance)
                .filter { it.artist != null }
                .map { it.artist!! }
                .doOnNext { performanceList.add(it) }
                .map { it.displayName!! }
                .toList()
                .subscribe(Consumer {
                    youtubeQueryLiveData.value = it[0]
                })
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
