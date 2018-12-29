package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.base.BaseDiscogsInteractor
import homepunk.github.com.domain.model.search.SearchResult
import homepunk.github.com.domain.repository.DiscogsDatabaseRepository
import io.reactivex.Observable
import javax.inject.Inject

/**Created by Homepunk on 28.12.2018. **/
class DiscogsReleaseInteractor @Inject constructor(discogsDatabaseRepository: DiscogsDatabaseRepository) : BaseDiscogsInteractor(discogsDatabaseRepository) {

    fun getLatestReleases(): Observable<SearchResult> {
        val paramsMap = mutableMapOf<String, String>()
        paramsMap[SEARCH_KEY_TYPE] = SEARCH_VALUE_RELEASE
        paramsMap[SEARCH_KEY_YEAR] = "2018"
        return discogsDatabaseRepository.search(paramsMap)
                .flatMapObservable { Observable.fromIterable(it) }
    }
}