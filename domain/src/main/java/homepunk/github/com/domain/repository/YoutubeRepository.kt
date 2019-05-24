package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.youtube.YoutubeVideo
import io.reactivex.Observable

/**Created by Homepunk on 23.05.2019. **/
interface YoutubeRepository {
    fun searchVideo(query: String, maxResults: Long): Observable<YoutubeVideo>
}