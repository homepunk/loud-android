package homepunk.github.com.data.remote

import homepunk.github.com.data.model.release.ReleaseListResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DiscogsApi {
    @GET("artists/1/releases?page=1&per_page=75")
    fun getReleaseList(): Single<ReleaseListResponse>
    //    @GET
//    fun getArtist(@Path name: String)
}