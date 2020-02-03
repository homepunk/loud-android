package homepunk.github.com.data.remote.repository

import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.data.remote.SongkickApi
import homepunk.github.com.data.remote.model.songkick.SongkickDataMapper
import homepunk.github.com.domain.model.internal.CityLocation
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.SongkickLocationRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongkickLocationDataRepository @Inject constructor(private var songkickApi: SongkickApi) : SongkickLocationRepository {

    override fun getSongkickLocationByQuery(query: String): Observable<CityLocation> =
            Observable.defer {
                songkickApi.getLocationByQuery(query, Constant.SONGKICK.API_KEY)
                        .subscribeOn(Schedulers.io())
                        .doOnError { it.printStackTrace() }
                        .map { it.resultsPage }
                        .flatMapObservable {
                            if (it.results != null && !it.results!!.location.isNullOrEmpty())
                                Observable.fromIterable(it.results!!.location)
                            else
                                Observable.empty<SongkickLocation>()
                        }
                        .map(SongkickDataMapper::map)
            }

    override fun getSongkickLocationByLatLng(latLng: String): Observable<CityLocation> =
            songkickApi.getLocationByLatLng(latLng, Constant.SONGKICK.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .doOnError { it.printStackTrace() }
                    .map { it.resultsPage }
                    .flatMapObservable {
                        if (it.results != null && !it.results!!.location.isNullOrEmpty())
                            Observable.fromIterable(it.results!!.location)
                        else
                            Observable.empty<SongkickLocation>()
                    }
                    .map(SongkickDataMapper::map)
}