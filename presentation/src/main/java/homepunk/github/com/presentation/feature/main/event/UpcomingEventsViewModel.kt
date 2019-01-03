package homepunk.github.com.presentation.feature.main.event

import android.annotation.SuppressLint
import android.content.Context
import homepunk.github.com.domain.interactor.DiscogsReleaseInteractor
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.main.discover.DiscoverFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject
import timber.log.Timber

class UpcomingEventsViewModel @Inject constructor(mContext: Context)
    : BaseViewModel<EventFragment>(mContext) {

    @Inject
    lateinit var eventInteractor: SongkickEventInteractor

    override fun init() {
    }

    @SuppressLint("CheckResult")
    fun getUpcomingEventList() {
        eventInteractor.getUpcomingEventList()
                .doOnNext { Timber.w("${it.displayName}") }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ getView()?.run {
                    upcomingEventRvAdapter.items = it
                } }, Throwable::printStackTrace)
    }

}
