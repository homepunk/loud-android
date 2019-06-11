package homepunk.github.com.presentation.feature.main

import android.view.View
import androidx.fragment.app.Fragment
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.menu.MenuModeModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.event.timeline.EventTimelineFragment
import homepunk.github.com.presentation.feature.releases.DiscoverHostFragment
import javax.inject.Inject

class MainViewModel @Inject constructor(var appDataFactory: AppDataFactory,
                                        var appModeInteractor: AppModeInteractor) : BaseViewModel() {

    var fragmentList = arrayListOf<Fragment>()
    var modeList = mutableListOf<MenuModeModel>()
    var filers = appDataFactory.getFilters()

    var onModeClickListener = View.OnClickListener { v ->
        v?.let {
            when (it.id) {
                R.id.btn_event -> {
                    appModeInteractor.changeAppMode(AppMode.EVENTS)
                }
                R.id.btn_releases -> {
                    appModeInteractor.changeAppMode(AppMode.RELEASES)
                }
            }
        }
    }

    init {
        modeList = appDataFactory.getModeList()
        fragmentList.add(DiscoverHostFragment())
        fragmentList.add(EventTimelineFragment())
        fragmentList.add(Fragment())
    }
}

