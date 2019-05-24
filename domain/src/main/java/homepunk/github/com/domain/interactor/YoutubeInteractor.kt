package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.youtube.YoutubeVideo
import homepunk.github.com.domain.repository.YoutubeRepository
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

/**Created by Homepunk on 23.05.2019. **/

@Singleton
class YoutubeInteractor @Inject constructor(val youtubeRepository: YoutubeRepository) {
    private val MAX_RESULTS = 5

    fun getVideoList(queries: List<String>): Observable<YoutubeVideo> {
        val maxResults = when (queries.size) {
            in 1 .. 5 -> Math.round((MAX_RESULTS / queries.size).toDouble())
            else -> 1
        }

        return Observable.fromIterable(queries)
                .flatMap { youtubeRepository.searchVideo(it, maxResults) }
    }
}