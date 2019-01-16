package homepunk.github.com.domain.interactor

import homepunk.github.com.domain.base.BaseSongkickInteractor
import homepunk.github.com.domain.repository.LocationRepository
import homepunk.github.com.domain.repository.SongkickEventRepository
import javax.inject.Inject

class SongkickEventInteractor @Inject constructor(private val songkickEventRepository: SongkickEventRepository, locationRepository: LocationRepository)
    : BaseSongkickInteractor(locationRepository) {

    fun getUpcomingEventList() =
            locationRepository.getSongkickLocation("Kharkiv")
                    .map { it.metroArea?.id }
                    .flatMap { songkickEventRepository.getUpcomingEventList(it) }
}