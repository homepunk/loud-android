package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.youtube.YoutubeVideoPreview
import io.reactivex.Observable

/**Created by Homepunk on 23.05.2019. **/
interface YoutubeRepository {
    fun searchVideo(query: String): Observable<YoutubeVideoPreview>
}