package homepunk.github.com.data.remote.model.songkick

data class BaseSongkickResponse<T>(
        var resultsPage: SongkickResponseResult<T>?) {

    data class SongkickResponseResult<T>(
            var status: String = "",
            var results: T?,
            var perPage: Int = 0,
            var page: Int = 0,
            var totalEntries: Int)
}

