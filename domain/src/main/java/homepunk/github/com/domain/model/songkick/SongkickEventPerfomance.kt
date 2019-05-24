package homepunk.github.com.domain.model.songkick

import java.io.Serializable

data class SongkickEventPerfomance(
    var id : Long,
    var displayName : String = "",
    var billing : String = "",
    var billingIndex : Int,
    var artist : SongkickArtist?
) : Serializable
