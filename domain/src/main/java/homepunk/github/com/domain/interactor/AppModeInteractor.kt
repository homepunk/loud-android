package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.model.AppMode
import homepunk.github.com.domain.repository.AppModeRepository
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
class AppModeInteractor @Inject constructor(val appModeRepository: AppModeRepository) {
    fun getCurrentAppMode() = appModeRepository.getCurrentMode()


    fun changeAppMode(mode: AppMode) {
        appModeRepository.changeMode(mode)
    }
}