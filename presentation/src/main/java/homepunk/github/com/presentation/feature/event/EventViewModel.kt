package homepunk.github.com.presentation.feature.event

import android.annotation.SuppressLint
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.common.adapter.timeline.TimelineEventAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.subList
import homepunk.github.com.presentation.core.ext.toArrayList
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.artist.model.ArtistModel
import homepunk.github.com.presentation.feature.event.model.EventModel
import homepunk.github.com.presentation.feature.event.model.VenueModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**Created by Homepunk on 08.04.2019. **/
class EventViewModel @Inject constructor(var eventInteractor: SongkickEventInteractor) : BaseViewModel() {
    lateinit var eventModel: EventModel

    var timelineEventAdapter = TimelineEventAdapter()

    var venueModel = ObservableField<VenueModel>(VenueModel())

    var listenToArtistModel: ArtistModel? = null
    var lineUpList = ObservableArrayList<ArtistModel>()
    var onArtistClickLiveData = MutableLiveData<ArtistModel>()
    var onArtistClickListener = object : OnItemClickListener<ArtistModel> {
        override fun onClick(position: Int, item: ArtistModel) {
            setUpListenToArtist(item, false)
        }
    }

    private fun setUpListenToArtist(artist: ArtistModel, play: Boolean) {
        fun play(artist: ArtistModel) {
            onArtistClickLiveData.value = artist.apply {
                isArtistClicked.set(true)
                isPlayClicked.set(play)
            }
        }

        listenToArtistModel?.let { currentArtist ->
            if (currentArtist.id != artist.id) {
                currentArtist.isArtistClicked.set(false)
                currentArtist.isPlayClicked.set(false)
            } else if (play) {
                play(currentArtist)
                return
            }
        }

        listenToArtistModel = artist.apply { play(this) }

    }

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
                    setUpVenueInfo(it)
                })
    }

    @SuppressLint("CheckResult")
    private fun setUpLineUp(it: SongkickEvent) {
        Observable.fromIterable(it.performance)
                .filter { it.artist != null }
                .map {
                    ArtistModel(it.artist!!.displayName, it.artist!!.id, it.billingIndex == 1)
                }
                .subscribe {
                    lineUpList.add(it)
                    if (it.isSelected) {
                        setUpListenToArtist(it, false)
                    }
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
