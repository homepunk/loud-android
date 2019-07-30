package homepunk.github.com.presentation.feature.menu.country

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.FlexLayoutRecyclerAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentCountryListBinding

/**Created by Homepunk on 26.02.2019. **/
class CountryListFragment : BaseFragment<FragmentCountryListBinding>() {
    private lateinit var viewModel: CountryListViewModel

    override var layoutId = R.layout.fragment_country_list

    override fun init() {
        viewModel = getViewModel(CountryListViewModel::class.java)
        mDataBinding.viewModel = viewModel
        mDataBinding.rvCountries.adapter = FlexLayoutRecyclerAdapter(R.layout.layout_item_country, BR.model)
    }
}