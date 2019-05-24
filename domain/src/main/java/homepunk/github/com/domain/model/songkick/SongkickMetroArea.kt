package homepunk.github.com.domain.model.songkick

import java.io.Serializable

data class SongkickMetroArea(
        val country: SongkickCountry? = null,
        var lat: Double,
        var lng: Double,
        var displayName: String = "",
        var uri: String = "",
        var id: Long
) : Serializable
