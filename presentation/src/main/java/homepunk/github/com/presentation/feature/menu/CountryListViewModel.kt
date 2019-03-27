package homepunk.github.com.presentation.feature.menu

import androidx.databinding.ObservableArrayList
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.menu.location.CountryParentCityChildModel
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryListViewModel @Inject constructor(var appDataFactory: AppDataFactory) : BaseViewModel() {

    var parentWithChildrenList = ObservableArrayList<CountryParentCityChildModel>()

    override fun init() {
        appDataFactory.getCountryModelList().run {
            forEach {
                parentWithChildrenList.add(CountryParentCityChildModel(it))
            }
        }
    }
}