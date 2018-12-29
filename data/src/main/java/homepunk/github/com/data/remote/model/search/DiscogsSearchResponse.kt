package homepunk.github.com.data.remote.model.search

import homepunk.github.com.data.remote.model.Pagination
import homepunk.github.com.domain.model.search.SearchResult

class DiscogsSearchResponse {
    var pagination: Pagination? = null
    var results: List<SearchResult>? = listOf()
}