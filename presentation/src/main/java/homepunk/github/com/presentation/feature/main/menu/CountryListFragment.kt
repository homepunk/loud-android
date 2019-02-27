package homepunk.github.com.presentation.feature.main.menu

import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentCountryListBinding

/**Created by Homepunk on 26.02.2019. **/
class CountryListFragment : BaseFragment<FragmentCountryListBinding>() {
    private lateinit var viewModel: CountryListViewModel

    override fun getLayoutResId() = R.layout.fragment_country_list

    override fun init() {
        viewModel = getViewModel(CountryListViewModel::class.java)
        viewModel.init()
        mDataBinding.viewModel = viewModel
    }
}