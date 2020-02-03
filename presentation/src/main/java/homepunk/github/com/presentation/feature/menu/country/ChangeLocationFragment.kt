package homepunk.github.com.presentation.feature.menu.country

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.support.AndroidSupportInjection
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.FlexLayoutRecyclerAdapter
import homepunk.github.com.presentation.core.ext.getViewModel
import homepunk.github.com.presentation.databinding.FragmentChangeLocationBinding
import javax.inject.Inject


/**Created by Homepunk on 26.02.2019. **/
class ChangeLocationFragment : BottomSheetDialogFragment() {
    private lateinit var viewModel: ChangeLocationViewModel
    private lateinit var mBinding: FragmentChangeLocationBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_change_location, container, false)
        viewModel = getViewModel(ChangeLocationViewModel::class.java, viewModelFactory)
                .apply {

                }
        mBinding.apply {
            changeLocationViewModel = viewModel

            rvCountries.adapter = FlexLayoutRecyclerAdapter(R.layout.layout_item_country, BR.model)
        }
        return mBinding.root
    }
}