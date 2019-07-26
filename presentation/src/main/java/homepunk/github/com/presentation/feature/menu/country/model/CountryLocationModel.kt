package homepunk.github.com.presentation.feature.menu.country.model

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.model.CountryModel

/**Created by Homepunk on 26.07.2019. **/
class CountryLocationModel(val countryModel: CountryModel, locations: List<UserLocation>, savedLocations: List<UserLocation>) {
    var isParentExpanded = ObservableBoolean(false)

    private var countryLocationList = ObservableArrayList<UserLocation>()
    private var savedLocationList = ObservableArrayList<UserLocation>()

    var savedLocationListAdapter = SimpleBindingRecyclerAdapter(R.layout.layout_item_location_child, BR.model, savedLocationList)
    var countryLocationListAdapter = SimpleBindingRecyclerAdapter(R.layout.layout_item_location_child, BR.model, countryLocationList)

    init {
        countryLocationList.addAll(locations)
        savedLocationList.addAll(savedLocations)
    }
}