package homepunk.github.com.presentation.feature.main.home

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.base.BaseFragment
import homepunk.github.com.presentation.databinding.FragmentHomeBinding
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter
import homepunk.github.com.presentation.util.decoration.MarginItemDecoration
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment<HomeFragmentViewModel, FragmentHomeBinding>() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var latestReleaseRvAdapter: LatestReleaseListRecyclerAdapter

    override fun createViewModel() = ViewModelProviders.of(this, viewModelFactory)[HomeFragmentViewModel::class.java]

    override fun getLayoutResId() = R.layout.fragment_home

    override fun init() {
        setupRecycler()
        mViewModel.getLatestReleases()
                .doOnNext { Timber.d("Title = ${it.title} , type = ${it.type}") }
                .toList()
                .doOnError { it.printStackTrace() }
                .subscribe { it -> latestReleaseRvAdapter.items = it }
    }

    private fun setupRecycler() {
        latestReleaseRvAdapter = LatestReleaseListRecyclerAdapter()
        mDataBinding.run {
            rvLatestReleases.adapter = latestReleaseRvAdapter
            rvLatestReleases.addItemDecoration(MarginItemDecoration(
                    getDimen(R.dimen.margin_big_20dp).toInt(),
                    getDimen(R.dimen.margin_default_10dp).toInt()))
        }
    }
}
