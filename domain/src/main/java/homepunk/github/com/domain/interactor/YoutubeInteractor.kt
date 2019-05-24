package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.youtube.YoutubeVideoPreview
import homepunk.github.com.domain.repository.YoutubeRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**Created by Homepunk on 23.05.2019. **/

@Singleton
class YoutubeInteractor @Inject constructor(val youtubeRepository: YoutubeRepository) {

    fun getVideoList(query: String): Observable<YoutubeVideoPreview> {
        return youtubeRepository.searchVideo(query)
    }
}