package homepunk.github.com.domain.model.songkick

import java.io.Serializable

data class SongkickEvent(
        var id: Long,
        var displayName: String = "",
        var type: String = "",
        var uri: String = "",
        var status: String = "",
        var popularity: Double,
        var start: SongkickEventTime? = null,
        var performance: List<SongkickEventPerfomance> = mutableListOf(),
        var ageRestriction: String = "N/A",
        var venue: SongkickVenue
//    "flaggedAsEnded":false,
//    "location":{  }
) : Serializable