package homepunk.github.com.domain.repository

import homepunk.github.com.domain.model.AppMode
import io.reactivex.Observable

/**Created by Homepunk on 18.01.2019. **/
interface AppModeRepository {
    fun getCurrentMode(): Observable<AppMode>

    fun changeMode(mode: AppMode)
}