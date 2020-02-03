package homepunk.github.com.presentation.feature.menu.country.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import homepunk.github.com.domain.model.internal.CityLocation
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.model.CountryModel

/**Created by Homepunk on 26.07.2019. **/
class CountryViewModel(val countryModel: CountryModel,
                       var isLocationChangeMode: ObservableBoolean,
                       cities: List<CityLocation>, userCities: List<CityLocation>) : BaseObservable(){

    var isSelected = ObservableBoolean(false)
    private var countryCities = ObservableArrayList<CityLocation>()
    private var countryUserCities = ObservableArrayList<CityLocation>()

    val countryCitiesAdapter by lazy { SimpleBindingRecyclerAdapter(R.layout.layout_item_location_child_not_favorite, BR.model, countryCities)  }
    val countryUserCitiesAdapter by lazy { SimpleBindingRecyclerAdapter(R.layout.layout_item_location_child, BR.model, countryUserCities)  }

    init {
        countryCities.addAll(cities)
        countryUserCities.addAll(if (userCities.size > 4) userCities.subList(0, 4) else userCities)
    }

}