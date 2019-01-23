package homepunk.github.com.presentation.feature.discover.event.upcoming

import android.annotation.SuppressLint
import homepunk.github.com.domain.interactor.SongkickEventInteractor
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class EventListViewModel @Inject constructor()
    : BaseViewModel() {

    @Inject
    lateinit var eventInteractor: SongkickEventInteractor

    val adapter: SimpleBindingRecyclerViewAdapter<EventLocationModel> = SimpleBindingRecyclerViewAdapter(R.layout.layout_item_parent_children, BR.parentModel)

    override fun init() {
    }

    @SuppressLint("CheckResult")
    fun fetchUpcomingEventList() {
        eventInteractor.getUpcomingEventList()
//                .doOnNext { wLog(it.displayName) }
                .doOnNext { wLog("Location: ${it.first.city?.displayName}, events = ${it.second.size}") }
                .map { EventLocationModel(it.first.city?.displayName, it.second) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {itemList -> adapter.itemList = itemList}
    }
}
