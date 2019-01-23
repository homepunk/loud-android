package homepunk.github.com.data.remote.repository

import homepunk.github.com.data.remote.SongkickApi
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.domain.repository.SongkickEventRepository
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SongkickEventDataRepository @Inject constructor(private var songkickApi: SongkickApi) : SongkickEventRepository {
    override fun getUpcomingEventList(metroAreaId: Long): Observable<List<SongkickEvent>> {
        return songkickApi.getEventsAtLocation(metroAreaId)
                .subscribeOn(Schedulers.io())
                .doOnError { it.printStackTrace() }
                .map { it.resultsPage }
                .flatMapObservable {
                    if (it.results != null && !it.results!!.event.isNullOrEmpty())
                        Observable.just(it.results!!.event)
                    else
                        Observable.empty<List<SongkickEvent>>() }
    }
}