package homepunk.github.com.presentation.feature.discover

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.discover.event.EventDiscoverFragment
import homepunk.github.com.presentation.feature.discover.library.LibraryDiscoverFragment
import javax.inject.Inject

class DiscoverHostViewModel @Inject constructor() : BaseViewModel() {
    @Inject lateinit var appModeInteractor: AppModeInteractor

    val fragmentLiveData: MutableLiveData<Fragment> = MutableLiveData()

    override fun init() {
        compositeDisposable.add(appModeInteractor.getCurrentAppMode()
                .subscribe {
                    when (it) {
                        AppMode.LIBRARY -> {
                            fragmentLiveData.value = LibraryDiscoverFragment()
                        }
                        AppMode.EVENTS -> {
                            fragmentLiveData.value = EventDiscoverFragment()
                        }
                        else -> {
                        }
                    }
                })
    }
}
