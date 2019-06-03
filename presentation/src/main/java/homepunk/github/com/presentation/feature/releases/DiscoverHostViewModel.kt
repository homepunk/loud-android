package homepunk.github.com.presentation.feature.releases

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.event.home.EventListFragment
import homepunk.github.com.presentation.feature.releases.library.DiscoverLibraryFragment
import javax.inject.Inject

class DiscoverHostViewModel @Inject constructor(var appModeInteractor: AppModeInteractor) : BaseViewModel() {

    val fragmentLiveData: MutableLiveData<Fragment> = MutableLiveData()

    init {
        compositeDisposable.add(appModeInteractor.getAppMode()
                .subscribe {
                    when (it) {
                        AppMode.LIBRARY -> {
                            fragmentLiveData.value = DiscoverLibraryFragment()
                        }
                        AppMode.EVENTS -> {
                            fragmentLiveData.value = EventListFragment()
                        }
                        else -> {
                        }
                    }
                })
    }
}
