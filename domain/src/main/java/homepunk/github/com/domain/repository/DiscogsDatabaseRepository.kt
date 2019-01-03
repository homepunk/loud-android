package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.discogs.search.SearchResult
import io.reactivex.Single

/**Created by Homepunk on 28.12.2018. **/
interface DiscogsDatabaseRepository {
    fun search(paramsMap: Map<String, String>): Single<List<SearchResult>>
}