package homepunk.github.com.presentation.feature.main.discover

import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.domain.model.discogs.search.SearchResult
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.section.DiscoverSectionModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DiscoverViewModel @Inject constructor()
    : BaseViewModel() {

    @Inject
    lateinit var appDataFactory: AppDataFactory

    @Inject
    lateinit var discogsReleaseInteractor: DiscogsReleaseInteractor


    override fun init() {

    }

    fun getDiscoverSectionObservable(): Observable<DiscoverSectionViewModel> {
        return Observable.fromIterable(appDataFactory.getDiscoverLibrarySectionList())
                .doOnError { it.printStackTrace() }
                .flatMap {
                    Observable.zip(Observable.just(it), getDiscogsDiscoverLatestByType(it.type),
                            BiFunction { section: DiscoverSectionModel, dataList: List<SearchResult> ->
                                DiscoverSectionViewModel(section, dataList)
                            })
                }
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun getDiscogsDiscoverLatestByType(type: String) = getNotEmptyListObservable(
            if (type == Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE) {
                discogsReleaseInteractor.getLatestReleases()
            } else {
                discogsReleaseInteractor.getLatestGenreList(type)
            })

    private fun <T> getNotEmptyListObservable(source: Observable<T>) = source.toList().filter { it.isNotEmpty() }.toObservable()

}
