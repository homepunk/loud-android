package homepunk.github.com.data.remote

import homepunk.github.com.data.remote.model.songkick.BaseSongkickResponse
import homepunk.github.com.data.remote.model.songkick.SongkickUpcomingEventResponse
import homepunk.github.com.data.remote.model.songkick.SongkickLocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SongkickApi {
    @GET("metro_areas/{metroAreaId}/calendar.json?apikey=rX8RhAq6lkDw5OnK")
    fun getEventsAtLocation(@Path("metroAreaId") metroAreaId: Long): Single<BaseSongkickResponse<SongkickUpcomingEventResponse>>

    @GET("search/locations.json?location=geo:{latLng}&apikey=rX8RhAq6lkDw5OnK")
    fun getLocationByLatLng(@Path("latLng") latLng: String): BaseSongkickResponse<SongkickLocationResponse>

    @GET("search/locations.json")
    fun getLocationByQuery(@Query("query") query: String, @Query("apikey") apikey: String): Single<BaseSongkickResponse<SongkickLocationResponse>>
}