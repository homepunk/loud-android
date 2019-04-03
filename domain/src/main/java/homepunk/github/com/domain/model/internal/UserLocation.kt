package homepunk.github.com.domain.model.internal

import homepunk.github.com.domain.model.songkick.SongkickLocation

/**Created by Homepunk on 29.03.2019. **/
data class UserLocation(var countryName: String,
                        var locations: List<SongkickLocation>)