package homepunk.github.com.data.repository

import homepunk.github.com.data.model.DiscogsArtist
import saschpe.discogs.model.release.Artist

object DiscogsMapper {
    fun map(fromModel: Artist) = DiscogsArtist (
            fromModel.anv,
            fromModel.id,
            fromModel.join,
            fromModel.name,
            fromModel.resourceUrl,
            fromModel.role,
            fromModel.tracks)
}