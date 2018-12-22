package homepunk.github.com.data.repository

import homepunk.github.com.common.repository.DiscogsRepository
import homepunk.github.com.data.Constant
import homepunk.github.com.data.base.BaseRepository
import homepunk.github.com.data.remote.DiscogsApi
import io.reactivex.Single
import io.reactivex.SingleEmitter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import saschpe.discogs.Discogs
import saschpe.discogs.model.database.Search
import saschpe.discogs.model.release.Artist
import saschpe.discogs.service.DatabaseService.Companion.SEARCH_ARTIST
import saschpe.discogs.service.DatabaseService.Companion.SEARCH_TRACK

class DiscogsDataRepository /*@Inject*/ constructor(private var discogsApi: DiscogsApi?) : BaseRepository(), DiscogsRepository {
    private lateinit var mDiscogsClient: Discogs

    fun init() {
        mDiscogsClient = Discogs(
                Constant.USER_AGENT,
                Constant.CONSUMER_KEY,
                Constant.CONSUMER_SECRET,
                Constant.CURRENT_TOKEN)
    }

    fun getReleaseList() {
        discogsApi?.getReleases()
    }
}