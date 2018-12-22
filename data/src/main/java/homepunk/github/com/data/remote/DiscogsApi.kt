package homepunk.github.com.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface DiscogsApi {
    @GET("artists/1/releases?page=1&per_page=75")
    fun getReleases()
//    @GET
//    fun getArtist(@Path name: String)
}