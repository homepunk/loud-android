package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.domain.repository.AppModeRepository
import javax.inject.Inject
import javax.inject.Singleton

/**Created by Homepunk on 18.01.2019. **/
@Singleton
class AppModeInteractor @Inject constructor(val appModeRepository: AppModeRepository) {
    lateinit var currentAppMode: AppMode

    fun getAppMode() = appModeRepository.getCurrentMode()
            .doOnNext { currentAppMode = it }
            .distinctUntilChanged()

    fun changeAppMode(mode: AppMode) {
        appModeRepository.changeMode(mode)
    }
}