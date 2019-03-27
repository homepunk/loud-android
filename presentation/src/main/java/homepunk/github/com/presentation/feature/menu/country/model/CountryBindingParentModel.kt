package homepunk.github.com.presentation.feature.menu.country.model

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingParentModel
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel

/**Created by Homepunk on 27.03.2019. **/
class CountryBindingParentModel(val countryModel: CountryModel,
                                var children: List<CityBindingChildModel>) : ExpandableBindingParentModel<CityBindingChildModel>(children) {
    override fun getBindingVariableId() = BR.model

    override fun getLayoutId() = R.layout.layout_item_location_parent
}