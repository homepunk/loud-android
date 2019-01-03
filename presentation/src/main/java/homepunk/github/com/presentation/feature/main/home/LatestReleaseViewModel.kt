package homepunk.github.com.presentation.feature.main.home

import android.content.Context
import homepunk.github.com.data.constant.Constant
import homepunk.github.com.data.constant.DataFactory
import homepunk.github.com.data.constant.model.LatestReleaseTypeModel
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.domain.model.search.SearchResult
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.observers.FutureObserver
import timber.log.Timber
import java.util.concurrent.Future
import javax.inject.Inject

class LatestReleaseViewModel @Inject constructor(mContext: Context)
    : BaseViewModel<HomeFragment>(mContext) {

    @Inject
    lateinit var discogsReleaseInteractor: DiscogsReleaseInteractor

    fun fetchAllLatest() {
        val sectionList = mutableListOf<Pair<LatestReleaseTypeModel, Observable<SearchResult>>>()

        DataFactory.getLatestReleaseSectionList(mContext).forEach { section ->
            if (section.type == Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE) {
                sectionList.add(Pair(section, discogsReleaseInteractor.getLatestReleases()))
            } else {
                sectionList.add(Pair(section, discogsReleaseInteractor.getLatestGenreList(section.type)))
            }
        }

        sectionList.forEach {pair ->
            pair.second.observeOn(AndroidSchedulers.mainThread())
                    .toList()
                    .filter {it.isNotEmpty() }
                    .doOnError { it.printStackTrace() }
                    .subscribe { releaseList ->
                        getView()?.run {
                            view?.run {
                                val adapter = LatestReleaseListRecyclerAdapter()
                                latestReleaseRvAdapterMap[pair.first.id] = adapter
                                adapter.items = releaseList
                                addSection(pair.first.title, adapter)
                            }
                        }
                    }
        }
    }
}
