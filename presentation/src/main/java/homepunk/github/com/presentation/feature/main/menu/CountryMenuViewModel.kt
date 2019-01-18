package homepunk.github.com.presentation.feature.main.menu

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryMenuViewModel @Inject constructor(var appDataFactory: AppDataFactory) : BaseObservable() {

    private val countries = arrayListOf<CountryModel>()

    @Bindable
    fun getCountries(): List<CountryModel> {
        return appDataFactory.getCountryModelList()
                .apply {
                    countries.addAll(this)
                }
    }
}