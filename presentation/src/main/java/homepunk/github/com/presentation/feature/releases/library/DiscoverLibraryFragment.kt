package homepunk.github.com.presentation.feature.releases.library

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import homepunk.github.com.data.core.constant.Constant
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentDiscoverBinding
import homepunk.github.com.presentation.databinding.LayoutSectionSquareHorizontalBinding


class DiscoverLibraryFragment : BaseFragment<FragmentDiscoverBinding>() {
    private lateinit var discoverViewModel: DiscoverLibraryViewModel

    override var layoutId = R.layout.fragment_discover

    override fun init() {
        discoverViewModel = getViewModel(DiscoverLibraryViewModel::class.java)
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = super.onCreateView(inflater, container, savedInstanceState)
        discoverViewModel.getDiscoverSectionObservable()
                .subscribe { addSection(inflater, it) }
        return root
    }

    private fun addSection(inflater: LayoutInflater, viewModel: LibraryDiscoverSectionViewModel) {
        mDataBinding.run {
            val binding = DataBindingUtil.inflate<LayoutSectionSquareHorizontalBinding>(inflater, R.layout.layout_section_square_horizontal,
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
