package homepunk.github.com.presentation.feature.main.discover

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingFragment
import homepunk.github.com.presentation.databinding.FragmentDiscoverBinding
import homepunk.github.com.presentation.databinding.LayoutSectionSquareHorizontalBinding


class DiscoverFragment : BaseBindingFragment<FragmentDiscoverBinding>() {
    private lateinit var discoverViewModel: DiscoverViewModel

    override fun getLayoutResId() = R.layout.fragment_discover

    override fun initViewModels() {
        discoverViewModel = getViewModel(DiscoverViewModel::class.java)
    }

    @SuppressLint("CheckResult")
    override fun init() {
        discoverViewModel.getDiscoverSectionObservable()
                .subscribe(this::addSection)
    }

    private fun addSection(viewModel: DiscoverSectionViewModel) {
        mDataBinding.run {
            val binding = DataBindingUtil.inflate<LayoutSectionSquareHorizontalBinding>(layoutInflater, R.layout.layout_section_square_horizontal,
                    sectionContainer, false)
            binding.viewModel = viewModel

            if (viewModel.sectionModel.type == Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE) {
                sectionContainer.addView(binding.root, 0)
            } else {
                sectionContainer.addView(binding.root)
            }
        }
    }
}
