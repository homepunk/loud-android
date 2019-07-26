package homepunk.github.com.presentation.feature.menu.country

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleExpandableBindingRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.core.ext.bindParentList
import homepunk.github.com.presentation.databinding.FragmentCountryListBinding
import homepunk.github.com.presentation.feature.menu.country.model.CityBindingChildModel
import homepunk.github.com.presentation.feature.menu.country.model.CountryBindingParentModel

/**Created by Homepunk on 26.02.2019. **/
class CountryListFragment : BaseFragment<FragmentCountryListBinding>() {
    private lateinit var viewModel: CountryListViewModel

    override var layoutId = R.layout.fragment_country_list

    override fun init() {
        viewModel = getViewModel(CountryListViewModel::class.java)
        mDataBinding.viewModel = viewModel
        mDataBinding.rvCountries.adapter = SimpleExpandableBindingRecyclerAdapter<CityBindingChildModel, CountryBindingParentModel>()
    }
}