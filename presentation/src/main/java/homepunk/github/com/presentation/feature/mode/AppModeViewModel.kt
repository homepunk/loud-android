package homepunk.github.com.presentation.feature.mode

import androidx.lifecycle.MutableLiveData
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.mode.AppModeModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import javax.inject.Inject

/**Created by Homepunk on 21.01.2019. **/
class AppModeViewModel @Inject constructor(var appDataFactory: AppDataFactory,
                                           var appModeInteractor: AppModeInteractor) : BaseViewModel() {

    var isThemeApplied = false
    var currentAppModeTheme: Int? = null
    val currentAppModeModelLiveData = MutableLiveData<AppModeModel>()

    fun subscribeOnModeChanges() {
        subscriptions.add(appModeInteractor.getAppMode()
                .map { mode -> appDataFactory.getAppModeModel(mode) }
                .doOnError { it.printStackTrace() }
                .doOnNext { currentAppModeModelLiveData.value = it }
                .doOnNext {
                    if (it.themeResId != currentAppModeTheme) {
                        isThemeApplied = false
                    }
                }
                .subscribe { currentAppModeTheme = it.themeResId })
    }
}