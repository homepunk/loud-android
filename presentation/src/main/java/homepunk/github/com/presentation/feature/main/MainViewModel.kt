package homepunk.github.com.presentation.feature.main

import androidx.fragment.app.Fragment
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.event.timeline.EventTimelineFragment
import homepunk.github.com.presentation.feature.event.timeline.EventTimelineViewModel
import homepunk.github.com.presentation.feature.releases.DiscoverHostFragment
import javax.inject.Inject

class MainViewModel @Inject constructor(var appDataFactory: AppDataFactory,
                                        var appModeInteractor: AppModeInteractor) : BaseViewModel() {

    var fragmentList = arrayListOf<Fragment>()

    init {
        fragmentList.add(DiscoverHostFragment())
        fragmentList.add(EventTimelineFragment())
        fragmentList.add(Fragment())
    }
}

