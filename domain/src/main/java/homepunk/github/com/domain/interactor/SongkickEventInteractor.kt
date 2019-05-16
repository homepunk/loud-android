package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.base.BaseSongkickInteractor
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.LocationRepository
import homepunk.github.com.domain.repository.SongkickEventRepository
import homepunk.github.com.domain.repository.UserLocationRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class SongkickEventInteractor @Inject constructor(private val songkickEventRepository: SongkickEventRepository,
                                                  locationRepository: LocationRepository,
                                                  private var userLocationRepository: UserLocationRepository)
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

    fun getUpcomingEventsFromUserList(): Observable<List<Pair<SongkickLocation, List<SongkickEvent>>>> {
        return userLocationRepository.getUserLocationListForCountry(userLocationRepository.getUserCountryName())
                .flatMapIterable { it.locations }
                .filter { it.metroArea != null }
                .flatMapSingle {
                    Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.metroArea!!.id),
                            BiFunction { location: SongkickLocation, events: List<SongkickEvent> ->
                                Pair(location, events)
                            }).toList()
                }
    }

    fun getUpcomingEventsForUserLocation(userLocation: UserLocation): Observable<Pair<SongkickLocation, List<SongkickEvent>>> {
        return Observable.fromIterable(userLocation.locations)
                .filter { it.metroArea != null }
                .flatMap {
                    Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.metroArea!!.id),
                            BiFunction { location: SongkickLocation, events: List<SongkickEvent> ->
                                Pair(location, events)
                            })
                }
    }

    fun getEventDetails(eventId: Long): Observable<SongkickEvent> {
        return songkickEventRepository.getEventDetails(eventId)
    }
}