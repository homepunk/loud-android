package homepunk.github.com.presentation.feature.main.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingFragment
import homepunk.github.com.presentation.databinding.FragmentHomeBinding
import homepunk.github.com.presentation.databinding.LayoutSectionSquareHorizontalBinding
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration
import timber.log.Timber
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {
    private lateinit var latestReleaseViewModel: LatestReleaseViewModel
    internal val latestReleaseRvAdapterMap = mutableMapOf<Int, LatestReleaseListRecyclerAdapter>()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initViewModels() {
        latestReleaseViewModel = getViewModel(LatestReleaseViewModel::class.java)
        latestReleaseViewModel.bind(this)
    }

    @SuppressLint("CheckResult")
    override fun init() {
        latestReleaseViewModel.fetchAllLatest()
    }

    fun addSection(title: String, rvAdapter: LatestReleaseListRecyclerAdapter) {
        mDataBinding.run {
            val binding = DataBindingUtil.inflate<LayoutSectionSquareHorizontalBinding>(
                    layoutInflater,
                    R.layout.layout_section_square_horizontal,
                    sectionContainer, true)
            binding.sectionDivider.sectionTitle = title
            binding.sectionRecycler.adapter = rvAdapter
            binding.sectionRecycler.addItemDecoration(MarginItemDecoration(
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    getDimen(R.dimen.margin_default_10dp).toInt()))
        }
    }
}
