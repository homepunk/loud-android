package homepunk.github.com.domain.model.internal

import homepunk.github.com.domain.model.songkick.SongkickLocation

/**Created by Homepunk on 29.03.2019. **/
data class UserEventLocationSettings(var currentUserCountryName: String,
                                     var currentUserLocationList: List<SongkickLocation>)