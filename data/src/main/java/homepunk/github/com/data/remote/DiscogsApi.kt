package homepunk.github.com.data.remote

import homepunk.github.com.data.remote.model.discogs.search.DiscogsSearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface DiscogsApi {
    @GET("database/search")
    fun search(@QueryMap options: Map<String, String>): Single<DiscogsSearchResponse>
}