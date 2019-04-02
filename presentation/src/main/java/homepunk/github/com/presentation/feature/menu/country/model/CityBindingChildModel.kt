package homepunk.github.com.presentation.feature.menu.country.model

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import homepunk.github.com.domain.model.songkick.SongkickLocation
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingChildModel

/**Created by Homepunk on 27.03.2019. **/
class CityBindingChildModel(var location: SongkickLocation, isSaved: Boolean = false) : ExpandableBindingChildModel() {
    var name = ObservableField<String>(location.city!!.displayName)
    var isSelected = ObservableBoolean(isSaved)

    override fun getBindingVariableId() = BR.model

    override fun getLayoutId() = R.layout.layout_item_location_child
}