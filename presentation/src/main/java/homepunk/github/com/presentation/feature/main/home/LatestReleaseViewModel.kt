package homepunk.github.com.presentation.feature.main.home

import android.content.Context
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.presentation.core.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class LatestReleaseViewModel @Inject constructor(mContext: Context)
    : BaseViewModel<HomeFragment>(mContext) {

    @Inject
    lateinit var discogsReleaseInteractor: DiscogsReleaseInteractor

    fun fetchLatestReleases() = discogsReleaseInteractor
            .getLatestReleases()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { Timber.d("Title = ${it.title} , type = ${it.type}") }
            .toList()
            .doOnError { it.printStackTrace() }
            .subscribe { it -> getView()?.run {
                latestReleaseRvAdapter.items = it
            }}

}
