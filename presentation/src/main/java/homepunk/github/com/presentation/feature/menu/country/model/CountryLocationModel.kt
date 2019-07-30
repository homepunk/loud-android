package homepunk.github.com.presentation.feature.menu.country.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import homepunk.github.com.domain.model.internal.UserLocation
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.model.CountryModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList

/**Created by Homepunk on 26.07.2019. **/
class CountryLocationModel(val countryModel: CountryModel, var locations: List<UserLocation>, savedLocations: List<UserLocation>) : BaseObservable(){
    var isParentExpanded = ObservableBoolean(false)

    private var countryLocationList = ObservableArrayList<UserLocation>()
    private var savedLocationList = ObservableArrayList<UserLocation>()

    private val countryLocationListAdapter by lazy { SimpleBindingRecyclerAdapter(R.layout.layout_item_location_child, BR.model, countryLocationList)  }

    @Bindable
    fun getAdapter(): SimpleBindingRecyclerAdapter<UserLocation> {
        countryLocationList.addAllToEmptyList(locations)
        return countryLocationListAdapter
    }
}