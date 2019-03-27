package homepunk.github.com.presentation.feature.menu

import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentCountryListBinding
import homepunk.github.com.presentation.feature.widget.countrypicker.CountryModel

/**Created by Homepunk on 26.02.2019. **/
class CountryListFragment : BaseFragment<FragmentCountryListBinding>() {
    private lateinit var viewModel: CountryListViewModel

    override fun getLayoutResId() = R.layout.fragment_country_list

    override fun init() {
        viewModel = getViewModel(CountryListViewModel::class.java)
        viewModel.init()
        mDataBinding.viewModel = viewModel
        mDataBinding.rvCountries.adapter =  SimpleBindingRecyclerViewAdapter<CountryModel>(R.layout.layout_item_parent_country_children_city, BR.model)
    }
}