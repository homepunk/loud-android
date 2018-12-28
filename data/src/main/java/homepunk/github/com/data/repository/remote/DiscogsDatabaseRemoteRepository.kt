package homepunk.github.com.data.repository.remote

import homepunk.github.com.data.remote.DiscogsApi
import homepunk.github.com.domain.repository.DiscogsDatabaseRepository
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**Created by Homepunk on 28.12.2018. **/
class DiscogsDatabaseRemoteRepository @Inject constructor(private var discogsApi: DiscogsApi) : DiscogsDatabaseRepository {
    override fun search(paramsMap: Map<String, String>) {
            discogsApi.search(paramsMap)
                    .subscribeOn(Schedulers.io())
                    .subscribe()
    }
}