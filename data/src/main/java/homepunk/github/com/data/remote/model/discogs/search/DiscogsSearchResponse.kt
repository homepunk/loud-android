package homepunk.github.com.data.remote.model.discogs.search

import homepunk.github.com.data.remote.model.discogs.DiscogsPagination
import homepunk.github.com.domain.model.discogs.search.SearchResult


class DiscogsSearchResponse {
    var pagination: DiscogsPagination? = null
    var results: List<SearchResult>? = listOf()
}