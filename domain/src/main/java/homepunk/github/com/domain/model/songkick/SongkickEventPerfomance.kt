package homepunk.github.com.domain.model.songkick

import homepunk.github.com.domain.model.discogs.DiscogsArtist

data class SongkickEventPerfomance(
    var id : Long,
    var displayName : String = "",
    var billing : String = "",
    var billingIndex : Int,
    var artist : SongkickArtist?
)
