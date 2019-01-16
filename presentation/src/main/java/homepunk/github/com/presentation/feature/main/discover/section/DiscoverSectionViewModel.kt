package homepunk.github.com.presentation.feature.main.discover.section

import androidx.databinding.BaseObservable
import homepunk.github.com.domain.model.discogs.search.SearchResult
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleBindingRecyclerViewAdapter

/**Created by Homepunk on 14.01.2019. **/
class DiscoverSectionViewModel(val sectionModel: DiscoverSectionModel) : BaseObservable() {
    val adapter: SimpleBindingRecyclerViewAdapter<SearchResult> = SimpleBindingRecyclerViewAdapter(R.layout.layout_item_latest_release, BR.data)

    init {
        adapter.itemList = sectionModel.dataList
    }
}