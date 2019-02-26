package homepunk.github.com.presentation.feature.discover

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.discover.event.DiscoverEventFragment
import homepunk.github.com.presentation.feature.discover.library.DiscoverLibraryFragment
import javax.inject.Inject

class DiscoverHostViewModel @Inject constructor() : BaseViewModel() {
    @Inject lateinit var appModeInteractor: AppModeInteractor

    val fragmentLiveData: MutableLiveData<Fragment> = MutableLiveData()

    override fun init() {
        compositeDisposable.add(appModeInteractor.getAppMode()
                .subscribe {
                    when (it) {
                        AppMode.LIBRARY -> {
                            fragmentLiveData.value = DiscoverLibraryFragment()
                        }
                        AppMode.EVENTS -> {
                            fragmentLiveData.value = DiscoverEventFragment()
                        }
                        else -> {
                        }
                    }
                })
    }
}
