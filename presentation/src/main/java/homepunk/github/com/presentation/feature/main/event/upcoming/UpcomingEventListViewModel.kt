package homepunk.github.com.presentation.feature.main.event.upcoming

import android.annotation.SuppressLint
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UpcomingEventListViewModel @Inject constructor()
    : BaseViewModel() {

    @Inject
    lateinit var eventInteractor: SongkickEventInteractor

    val adapter: SimpleBindingRecyclerViewAdapter<UpcomingEventItemViewModel> = SimpleBindingRecyclerViewAdapter(R.layout.layout_item_upcoming_event, BR.viewModel)
    val titleResId = R.string.title_upcoming_event

    override fun init() {
    }

    @SuppressLint("CheckResult")
    fun fetchUpcomingEventList() {
        eventInteractor.getUpcomingEventList()
                .doOnNext { wLog(it.displayName) }
                .map { UpcomingEventItemViewModel(it.displayName, it.performance[0].artist?.id?.toString()) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {itemList -> adapter.itemList = itemList}
    }
}
