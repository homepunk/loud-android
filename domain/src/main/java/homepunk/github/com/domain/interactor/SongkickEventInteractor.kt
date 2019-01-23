package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.base.BaseSongkickInteractor
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.LocationRepository
import homepunk.github.com.domain.repository.SongkickEventRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class SongkickEventInteractor @Inject constructor(private val songkickEventRepository: SongkickEventRepository, locationRepository: LocationRepository)
    : BaseSongkickInteractor(locationRepository) {

    fun getUpcomingEventList() =
            locationRepository.getSongkickLocationByLatLng("geo:49.9,36.2")
//                    .map { it.metroArea?.id }
                    .filter { it.metroArea != null }
                    .flatMap {
                        Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.metroArea!!.id),
                                BiFunction { location: SongkickLocation, eventList: List<SongkickEvent> ->
                                    Pair(location, eventList)
                                })
                    }
}