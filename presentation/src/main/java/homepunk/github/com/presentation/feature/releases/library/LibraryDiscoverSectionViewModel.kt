package homepunk.github.com.presentation.feature.releases.library

import androidx.databinding.BaseObservable
import homepunk.github.com.domain.model.discogs.search.SearchResult
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.model.section.DiscoverSectionModel
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter

/**Created by Homepunk on 14.01.2019. **/
class LibraryDiscoverSectionViewModel(val sectionModel: DiscoverSectionModel, itemList: List<SearchResult>) : BaseObservable() {
    val adapter: SimpleBindingRecyclerAdapter<SearchResult> = SimpleBindingRecyclerAdapter(R.layout.layout_item_latest_release, BR.data)

    init {
        adapter.itemList = itemList
    }
}