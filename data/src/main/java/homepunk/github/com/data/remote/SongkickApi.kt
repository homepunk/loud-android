package homepunk.github.com.data.remote

import homepunk.github.com.data.remote.model.songkick.BaseSongkickResponse
import homepunk.github.com.data.remote.model.songkick.SongkickLocationResponse
import homepunk.github.com.data.remote.model.songkick.SongkickUpcomingEventResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SongkickApi {
    @GET("metro_areas/{metroAreaId}/calendar.json?apikey=A2lsBUAKcGTUHv7K")
    fun getEventsAtLocation(@Path("metroAreaId") metroAreaId: Long): Single<BaseSongkickResponse<SongkickUpcomingEventResponse>>

    @GET("search/locations.json?location=geo:{latLng}&apikey=A2lsBUAKcGTUHv7K")
    fun getLocationByLatLng(@Path("latLng") latLng: String): BaseSongkickResponse<SongkickLocationResponse>

    @GET("search/locations.json")
    fun getLocationByQuery(@Query("query") query: String, @Query("apikey") apikey: String): Single<BaseSongkickResponse<SongkickLocationResponse>>
}