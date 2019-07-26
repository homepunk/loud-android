package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.domain.repository.SongkickEventRepository
import homepunk.github.com.domain.repository.SongkickLocationRepository
import homepunk.github.com.domain.repository.UserConfigurationRepository
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SongkickEventInteractor @Inject constructor(private val songkickEventRepository: SongkickEventRepository,
                                                  private val songkickLocationRepository: SongkickLocationRepository,
                                                  private val userConfigurationRepository: UserConfigurationRepository) {

    private var userLocation: UserLocation? = null

    fun getUpcomingEventList() =
            songkickLocationRepository.getSongkickLocationByLatLng("geo:49.9,36.2")
                    .subscribeOn(Schedulers.io())
                    .filter { it.id != 0L }
                    .flatMap {
                        Observable.zip(Observable.just(it), songkickEventRepository.getUpcomingEventList(it.id),
                                BiFunction { location: UserLocation, eventList: List<SongkickEvent> ->
                                    Pair(location, eventList)
                                })
                    }


    /*
        fun getUpcomingEventsForUserLocation(location: UserLocation): Observable<Pair<SongkickLocation, List<SongkickEvent>>> {
            if (upcomingEventsForUserLocationObservable == null ||
                    userLocation != location) {
                upcomingEventsForUserLocationObservable = Observable.fromIterable(location.locations)
                        .filter { it.songkickLocation.metroArea != null }
                        .map { it.songkickLocation }
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
    */
    fun getUpcomingEventsForUserLocation(location: UserLocation): Observable<List<SongkickEvent>> {
        return songkickEventRepository.getUpcomingEventList(location.id)
    }

    fun getEventDetails(eventId: Long): Observable<SongkickEvent> {
        return songkickEventRepository.getEventDetails(eventId)
    }
}