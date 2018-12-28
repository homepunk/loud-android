package homepunk.github.com.data.repository

import homepunk.github.com.common.repository.DiscogsRepository
import homepunk.github.com.data.Constant
import homepunk.github.com.data.base.BaseRepository
import homepunk.github.com.data.remote.DiscogsApi
import io.reactivex.schedulers.Schedulers
import saschpe.discogs.Discogs
import javax.inject.Inject

class DiscogsDataRepository @Inject constructor(private var discogsApi: DiscogsApi) : BaseRepository(), DiscogsRepository {
    private lateinit var mDiscogsClient: Discogs

    fun init() {
        mDiscogsClient = Discogs(
                Constant.USER_AGENT,
                Constant.CONSUMER_KEY,
                Constant.CONSUMER_SECRET,
                Constant.CURRENT_TOKEN)
    }

    override fun getReleaseList() {
        discogsApi.getReleaseList()
                .subscribeOn(Schedulers.io())
                .subscribe({}, Throwable::printStackTrace)
    }
}