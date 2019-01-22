package homepunk.github.com.presentation.feature.mode

import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import javax.inject.Inject

/**Created by Homepunk on 21.01.2019. **/
class AppModeViewModel @Inject constructor() : BaseViewModel() {
    @Inject lateinit var appDataFactory: AppDataFactory
    @Inject lateinit var appModeInteractor: AppModeInteractor

    var currentAppModeTheme: Int? = null
    val currentAppModeModelLiveData = MutableLiveData<AppModeModel>()

    fun subscribeOnModeChanges() {
        compositeDisposable.add(appModeInteractor.getCurrentAppMode()
                .distinctUntilChanged()
                .map { mode -> appDataFactory.getAppModeModelList().find { it.mode == mode }!! }
                .doOnError { it.printStackTrace() }
                .doOnNext { currentAppModeModelLiveData.value = it }
                .subscribe { currentAppModeTheme = it.themeResId })
    }
}