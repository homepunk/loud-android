package homepunk.github.com.presentation.feature.main.discover

import android.content.Context
import homepunk.github.com.data.constant.Constant
import homepunk.github.com.data.constant.DataFactory
import homepunk.github.com.data.constant.model.DiscogsDiscoverSection
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.domain.model.discogs.search.SearchResult
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.main.discover.section.DiscoverSectionModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class DiscoverViewModel @Inject constructor(val mContext: Context)
    : BaseViewModel() {

    @Inject
    lateinit var discogsReleaseInteractor: DiscogsReleaseInteractor


    override fun init() {

    }

    fun getDiscoverSectionObservable(): Observable<DiscoverSectionModel> {
        return Observable.fromIterable(DataFactory.getDiscogsDiscoverSectionList(mContext))
                .doOnError { it.printStackTrace() }
                .flatMap {
                    Observable.zip(Observable.just(it), getDiscogsDiscoverLatestByType(it.type),
                            BiFunction { section: DiscogsDiscoverSection, dataList: List<SearchResult> ->
                                DiscoverSectionModel(section.id, section.type, section.title, dataList)
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
