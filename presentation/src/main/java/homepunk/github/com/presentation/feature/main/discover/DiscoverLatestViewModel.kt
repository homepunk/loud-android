package homepunk.github.com.presentation.feature.main.discover

import android.content.Context
import homepunk.github.com.data.constant.Constant
import homepunk.github.com.data.constant.DataFactory
import homepunk.github.com.data.constant.model.LatestReleaseTypeModel
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.domain.model.discogs.search.SearchResult
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class DiscoverLatestViewModel @Inject constructor(mContext: Context)
    : BaseViewModel<DiscoverFragment>(mContext) {

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

        sectionList.forEach { pair ->
            pair.second.observeOn(AndroidSchedulers.mainThread())
                    .toList()
                    .filter { it.isNotEmpty() }
                    .doOnError { it.printStackTrace() }
                    .subscribe { releaseList ->
                        getView()?.run {
                            view?.run {
                                val adapter = LatestReleaseListRecyclerAdapter()
                                latestReleaseRvAdapterMap[pair.first.id] = adapter
                                adapter.items = releaseList
                                addSection(pair.first.title, pair.first.type, adapter)
                            }
                        }
                    }
        }
    }
}
