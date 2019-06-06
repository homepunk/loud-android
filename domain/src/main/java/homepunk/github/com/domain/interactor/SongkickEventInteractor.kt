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
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongkickEventInteractor @Inject constructor(private val songkickEventRepository: SongkickEventRepository,
                                                  locationRepository: LocationRepository,
                                                  private var userLocationRepository: UserLocationRepository)
    : BaseSongkickInteractor(locationRepository) {

    private var userLocation: UserLocation? = null
    private var upcomingEventsForUserLocationObservable: Observable<Pair<SongkickLocation, List<SongkickEvent>>>? = null

    fun getUpcomingEventList() =
            locationRepository.getSongkickLocationByLatLng("geo:49.9,36.2")
//                    .map { it.metroArea?.id }
                    .subscribeOn(Schedulers.io())
                    .filter { it.metroArea != null }
                    .flatMap {
                        Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.metroArea!!.id),
                                BiFunction { location: SongkickLocation, eventList: List<SongkickEvent> ->
                                    Pair(location, eventList)
                                })
                    }

    fun getUpcomingEventsFromUserList(): Observable<List<Pair<SongkickLocation, List<SongkickEvent>>>> {
        return userLocationRepository.getUserLocationListForCountry(userLocationRepository.getUserCountryName())
                .subscribeOn(Schedulers.io())
                .flatMapIterable { it.locations }
                .filter { it.metroArea != null }
                .flatMapSingle {
                    Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.metroArea!!.id),
                            BiFunction { location: SongkickLocation, events: List<SongkickEvent> ->
                                Pair(location, events)
                            }).toList()
                }
    }

    fun getUpcomingEventsForUserLocation(location: UserLocation): Observable<Pair<SongkickLocation, List<SongkickEvent>>> {
        if (upcomingEventsForUserLocationObservable == null ||
                userLocation != location) {
            upcomingEventsForUserLocationObservable = Observable.fromIterable(location.locations)
                    .filter { it.metroArea != null }
                    .flatMap {
                        Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.metroArea!!.id),
                                BiFunction { location: SongkickLocation, events: List<SongkickEvent> ->
                                    Pair(location, events)
                                })
                    }
                    .cache()
            userLocation = location
        }
        return upcomingEventsForUserLocationObservable!!
    }

    fun getEventDetails(eventId: Long): Observable<SongkickEvent> {
        return songkickEventRepository.getEventDetails(eventId)
    }
}