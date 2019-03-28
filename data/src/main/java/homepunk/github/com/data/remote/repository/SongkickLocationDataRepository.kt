package homepunk.github.com.data.remote.repository

import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.data.remote.SongkickApi
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.domain.repository.LocationRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongkickLocationDataRepository @Inject constructor(private var songkickApi: SongkickApi) : LocationRepository {

    override fun getSongkickLocationByQuery(query: String): Observable<SongkickLocation> =
            songkickApi.getLocationByQuery(query, Constant.SONGKICK.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .doOnError { it.printStackTrace() }
                    .map { it.resultsPage }
                    .flatMapObservable {
                        if (it.results != null && !it.results!!.location.isNullOrEmpty())
                            Observable.fromIterable(it.results!!.location)
                        else
                            Observable.empty<SongkickLocation>() }

    override fun getSongkickLocationByLatLng(latLng: String): Observable<SongkickLocation> =
            songkickApi.getLocationByLatLng(latLng, Constant.SONGKICK.API_KEY)
                    .subscribeOn(Schedulers.io())
                    .doOnError { it.printStackTrace() }
                    .map { it.resultsPage }
                    .flatMapObservable {
                        if (it.results != null && !it.results!!.location.isNullOrEmpty())
                            Observable.fromIterable(it.results!!.location)
                        else
                            Observable.empty<SongkickLocation>() }
}