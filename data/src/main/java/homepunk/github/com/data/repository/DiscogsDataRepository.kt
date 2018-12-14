package homepunk.github.com.data.repository

import homepunk.github.com.data.Constant
import homepunk.github.com.data.base.BaseRepository
import homepunk.github.com.domain.model.DiscogsArtist
import homepunk.github.com.domain.repository.DiscogsRepository
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
import kotlin.coroutines.experimental.coroutineContext

class DiscogsDataRepository : BaseRepository(), DiscogsRepository {
    lateinit var mDiscogsClient: Discogs

    fun init() {
        mDiscogsClient = Discogs(
                Constant.USER_AGENT,
                Constant.CONSUMER_KEY,
                Constant.CONSUMER_SECRET,
                Constant.CURRENT_TOKEN)

    }

    override fun getArtist(name: String?): Single<DiscogsArtist> {
        return Single.create { emitter: SingleEmitter<Artist> ->
            mDiscogsClient.database
                    .search(hashMapOf(SEARCH_ARTIST to "Nine Inch Nails"))
                    .enqueue(object : Callback<Search> {
                        override fun onResponse(call: Call<Search>, response: Response<Search>) {
                            response.body()?.results?.let {results ->
                                for (result in results) {
                                }
                            }
                        }

                        override fun onFailure(call: Call<Search>, t: Throwable) {
                            emitter.onError(t)
                        }
                    })
        }
                .map { DiscogsMapper.map(it) }
    }
}