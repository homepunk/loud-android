package homepunk.github.com.presentation.feature.main

import androidx.databinding.ObservableField
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.common.model.AppModeModel
import homepunk.github.com.presentation.core.base.BaseViewModel
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var appDataFactory: AppDataFactory

    var currentAppModeModel = ObservableField<AppModeModel>()
    var appModeModelList = arrayListOf<AppModeModel>()
        get() {
            field.addAll(appDataFactory.getAppModeModelList()
                    .apply {
                        if (currentAppModeModel.get() == null) {
                            currentAppModeModel.set(this[0])
                        }
                    }
            )
            return field
        }
}

