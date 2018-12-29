package homepunk.github.com.presentation.feature.main.home

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseBindingFragment
import homepunk.github.com.presentation.databinding.FragmentHomeBinding
import homepunk.github.com.presentation.databinding.LayoutSectionSquareHorizontalBinding
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration

class HomeFragment : BaseBindingFragment<FragmentHomeBinding>() {
    private lateinit var latestReleaseViewModel: LatestReleaseViewModel
    internal lateinit var latestReleaseRvAdapter: LatestReleaseListRecyclerAdapter

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initViewModels() {
        latestReleaseViewModel = getViewModel(LatestReleaseViewModel::class.java)
        latestReleaseViewModel.bind(this)
    }

    @SuppressLint("CheckResult")
    override fun init() {
        mDataBinding.run {
            latestReleaseRvAdapter = LatestReleaseListRecyclerAdapter()
            setupSquareHorizontalSection(latestReleaseSection, getString(R.string.title_latest_releases),
                    latestReleaseRvAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>)
            latestReleaseViewModel.fetchLatestReleases()
        }
    }

    private fun setupSquareHorizontalSection(sectionBinding: LayoutSectionSquareHorizontalBinding, title: String, rvAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        sectionBinding.run {
            sectionBinding.title = title
            sectionRecycler.adapter = rvAdapter
            sectionRecycler.addItemDecoration(MarginItemDecoration(
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    getDimen(R.dimen.margin_default_10dp).toInt()))
        }
    }

}
