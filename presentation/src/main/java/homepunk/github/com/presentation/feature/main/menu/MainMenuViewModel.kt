package homepunk.github.com.presentation.feature.main.menu

import android.content.res.Resources
import androidx.databinding.BaseObservable
import com.nightonke.boommenu.BoomButtons.BoomButton
import com.nightonke.boommenu.OnBoomListenerAdapter
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.menu.MenuModel
import io.reactivex.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**Created by Homepunk on 18.01.2019. **/
class MainMenuViewModel @Inject constructor() : BaseObservable() {
    @Inject lateinit var appDataFactory: AppDataFactory
    @Inject lateinit var appModeInteractor: AppModeInteractor

    var menuList = listOf<MenuModel>()
        get() {
            field = appDataFactory.getMenuList()
            return field
        }

    var onBoomMenuListener = object : OnBoomListenerAdapter() {
        override fun onClicked(index: Int, boomButton: BoomButton?) {
            Timber.d("onClicked $index")
            val menuModel = menuList[index]
            if (menuModel.isModeSelected) {
                appModeInteractor.changeAppMode(menuModel.appMode!!)
            }
        }
    }
}