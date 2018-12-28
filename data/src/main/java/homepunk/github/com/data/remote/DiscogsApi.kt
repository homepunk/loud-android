package homepunk.github.com.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap
import saschpe.discogs.model.database.Search

interface DiscogsApi {
    @GET("database/search")
    fun search(@QueryMap options: Map<String, String>): Single<Search>
}