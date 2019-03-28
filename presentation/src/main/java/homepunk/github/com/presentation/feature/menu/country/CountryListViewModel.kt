package homepunk.github.com.presentation.feature.menu.country

import androidx.databinding.ObservableArrayList
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryListViewModel @Inject constructor(var appDataFactory: AppDataFactory) : BaseViewModel() {

    var itemList = ObservableArrayList<CountryBindingParentModel>()

    init {
        appDataFactory.getCountryModelList().run {
            forEach {
                itemList.add(CountryBindingParentModel(it, arrayListOf(CityBindingChildModel("Kharkiv"), CityBindingChildModel("Odessa"))))
            }
        }
    }
}