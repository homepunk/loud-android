package homepunk.github.com.presentation.feature.main.discover

import android.annotation.SuppressLint
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingFragment
import homepunk.github.com.presentation.databinding.LayoutSectionSquareHorizontalBinding
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration
import timber.log.Timber
import androidx.databinding.DataBindingUtil
import homepunk.github.com.data.constant.Constant
import homepunk.github.com.presentation.databinding.FragmentDiscoverBinding


class DiscoverFragment : BaseBindingFragment<FragmentDiscoverBinding>() {
    private lateinit var discoverLatestViewModel: DiscoverLatestViewModel
    internal val latestReleaseRvAdapterMap = mutableMapOf<Int, LatestReleaseListRecyclerAdapter>()

    override fun getLayoutResId() = R.layout.fragment_discover

    override fun initViewModels() {
        discoverLatestViewModel = getViewModel(DiscoverLatestViewModel::class.java)
        discoverLatestViewModel.bind(this)
    }

    @SuppressLint("CheckResult")
    override fun init() {
        discoverLatestViewModel.fetchAllLatest()
    }

    fun addSection(title: String, type: String, rvAdapter: LatestReleaseListRecyclerAdapter) {
        mDataBinding.run {
            val binding = DataBindingUtil.inflate<LayoutSectionSquareHorizontalBinding>(layoutInflater, R.layout.layout_section_square_horizontal,
                    sectionContainer, false)
            binding.sectionDivider.sectionTitle = title
            binding.sectionRecycler.adapter = rvAdapter
            binding.sectionRecycler.addItemDecoration(MarginItemDecoration(
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    0,
                    getDimen(R.dimen.margin_default_10dp).toInt()))
            if (type == Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE) {
                sectionContainer.addView(binding.root, 0)
            } else {
                sectionContainer.addView(binding.root)
            }
        }
    }
}
