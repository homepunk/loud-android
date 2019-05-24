package homepunk.github.com.data.remote.repository

import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes
import com.google.api.services.youtube.model.SearchListResponse
import homepunk.github.com.data.remote.model.youtube.YoutubeSearchResultMapper
import homepunk.github.com.domain.model.youtube.YoutubeVideo
import homepunk.github.com.domain.repository.YoutubeRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton


/**Created by Homepunk on 22.05.2019. **/
@Singleton
class YoutubeDataRepository @Inject constructor() : YoutubeRepository {
    //Package name of the app that will call the YouTube Data API
    private val PACKAGENAME = "com.github.homepunk.vinylunderground"
    //SHA1 fingerprint of APP can be found by double clicking on the app signing report on right tab called gradle
    private val SHA1 = "40:E7:53:76:67:9C:30:F8:E4:AD:BC:06:98:0F:9E:95:DD:C1:D3:CE"
    //maximum results that should be downloaded via the YouTube data API at a time
    private val MAXRESULTS: Long = 25
    private val SCOPES = arrayOf(YouTubeScopes.YOUTUBE_READONLY)
    private var API_KEY = "AIzaSyBHmQY7e766b7Kcgtzw1dohgFjeadDFwU8"
    //Youtube object for executing api related queries through Youtube Data API
    private var youtube: YouTube
    private var youtubeSearchList: YouTube.Search.List

    init {
        youtube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), HttpRequestInitializer { request ->
            //initialize method helps to add any extra details that may be required to process the youtubeSearchList
            //setting package name and sha1 certificate to identify request by server
            request.headers.set("X-Android-Package", PACKAGENAME)
            request.headers.set("X-Android-Cert", SHA1)
        }).setApplicationName("SearchYoutube").build()

        // Define the API request for retrieving searchVideo results.
        youtubeSearchList = youtube.search().list("id,snippet")

        //setting API key to youtubeSearchList
        // Set your developer key from the {{ Google Cloud Console }} for
        // non-authenticated requests. See:
        // {{ https://cloud.google.com/console }}
        youtubeSearchList.key = API_KEY

        // Restrict the searchVideo results to only include videos. See:
        // https://developers.google.com/youtube/v3/docs/search/list#type
        youtubeSearchList.type = "video"

        //setting fields which should be returned
        //setting only those fields which are required
        //for maximum efficiency
        //here we are retreiving fiels:
        //-kind of video
        //-video ID
        //-title of video
        //-description of video
        //high quality thumbnail url of the video
        youtubeSearchList.fields = "items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)"
    }

    override fun searchVideo(query: String, maxResults: Long): Observable<YoutubeVideo> {
        return Single.just(youtubeSearchList)
                .subscribeOn(Schedulers.io())
                .doOnSuccess { it.maxResults = maxResults }
                .doOnSuccess { it.q = query }
                .map { it.execute() as SearchListResponse }
                .flatMapObservable { Observable.fromArray(it.items) }
                .flatMapIterable { it }
                .map { YoutubeSearchResultMapper.map(it) }
    }
}