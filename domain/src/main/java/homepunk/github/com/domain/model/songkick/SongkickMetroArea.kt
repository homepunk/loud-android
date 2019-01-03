package homepunk.github.com.domain.model.songkick

data class SongkickMetroArea(
        //    "country":{  },
        var lat: Double,
        var lng: Double,
        var displayName: String = "",
        var uri: String = "",
        var id: Long
)
