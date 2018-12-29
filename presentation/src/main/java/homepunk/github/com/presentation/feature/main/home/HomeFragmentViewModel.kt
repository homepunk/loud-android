package homepunk.github.com.presentation.feature.main.home

import android.content.Context
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.presentation.core.base.BaseViweModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class HomeFragmentViewModel @Inject constructor(mContext: Context)
    : BaseViweModel(mContext) {

    @Inject
    lateinit var discogsReleaseInteractor: DiscogsReleaseInteractor

    fun getLatestReleases() = discogsReleaseInteractor
            .getLatestReleases()
            .observeOn(AndroidSchedulers.mainThread())

}
