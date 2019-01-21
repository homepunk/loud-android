package homepunk.github.com.presentation.feature.main

import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var appDataFactory: AppDataFactory
    @Inject
    lateinit var appModeInteractor: AppModeInteractor

    val currentAppModeModelLiveData = MutableLiveData<AppModeModel>()

    fun setUp() {
        compositeDisposable.add(appModeInteractor.getCurrentAppMode()
                .map { mode -> appDataFactory.getAppModeModelList().find { it.mode == mode }!! }
                .doOnError { it.printStackTrace() }
                .subscribe {
                    currentAppModeModelLiveData.value = it
                })
    }
}

