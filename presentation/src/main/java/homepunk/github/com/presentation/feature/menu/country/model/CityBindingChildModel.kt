package homepunk.github.com.presentation.feature.menu.country.model

import androidx.databinding.ObservableField
import homepunk.github.com.domain.model.songkick.SongkickCity
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.model.ExpandableBindingChildModel

/**Created by Homepunk on 27.03.2019. **/
class CityBindingChildModel(city: SongkickCity) : ExpandableBindingChildModel() {
    var name = ObservableField<String>(city.displayName)

    override fun getBindingVariableId() = BR.model

    override fun getLayoutId() = R.layout.layout_item_location_child
}