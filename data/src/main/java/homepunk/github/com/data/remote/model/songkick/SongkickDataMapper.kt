package homepunk.github.com.data.remote.model.songkick

import homepunk.github.com.domain.model.internal.CityLocation
import homepunk.github.com.domain.model.songkick.SongkickLocation

/**Created by Homepunk on 11.07.2019. **/

object SongkickDataMapper {
    fun map(fromModel: SongkickLocation): CityLocation {
        return CityLocation(fromModel.metroArea?.id ?: 0,
                fromModel.city?.displayName ?: "Unknown",
                fromModel.metroArea?.country?.displayName ?: "Unknown",
                false)
    }
}