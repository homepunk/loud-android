package homepunk.github.com.data.remote.model.discogs.search

import homepunk.github.com.data.remote.model.discogs.Pagination
import homepunk.github.com.domain.model.discogs.search.SearchResult

class DiscogsSearchResponse {
    var pagination: Pagination? = null
    var results: List<SearchResult>? = listOf()
}