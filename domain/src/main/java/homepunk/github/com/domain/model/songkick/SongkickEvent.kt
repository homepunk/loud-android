package homepunk.github.com.domain.model.songkick

data class SongkickEvent (
        var id : Long,
        var displayName : String,
        var type : String,
        var uri : String,
        var status: String,
        var popularity : Double,
//    "start"
        var performance : List<SongkickEventPerfomance> = mutableListOf()
//    "ageRestriction":null,
//    "flaggedAsEnded":false,
//    "venue":{  },
//    "location":{  }
)
