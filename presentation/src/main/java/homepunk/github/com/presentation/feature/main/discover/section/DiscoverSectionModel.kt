package homepunk.github.com.presentation.feature.main.discover.section

import homepunk.github.com.domain.model.discogs.search.SearchResult

/**Created by Homepunk on 14.01.2019. **/
class DiscoverSectionModel(val id: Int,
                           val type: String,
                           val title: String) {
    var dataList: List<SearchResult> = arrayListOf()

    constructor(id: Int, type: String, title: String, dataList: List<SearchResult>) : this(id, type, title) {
        this.dataList = dataList
    }
}