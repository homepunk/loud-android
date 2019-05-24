package homepunk.github.com.domain.model.songkick

import java.io.Serializable

data class SongkickArtist(
        var id: Long,
        var displayName: String = "",
        var uri: String = ""
) : Serializable
