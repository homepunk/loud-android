package homepunk.github.com.presentation.feature.main.menu

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel
import javax.inject.Inject

/**Created by Homepunk on 17.01.2019. **/
class CountryListViewModel @Inject constructor(var appDataFactory: AppDataFactory) : BaseViewModel() {

    var adapter =  SimpleBindingRecyclerViewAdapter<CountryModel>(R.layout.layout_item_country, BR.model)

    override fun init() {
        appDataFactory.getCountryModelList()
                .apply {
                    adapter.itemList = this
                }
    }
}