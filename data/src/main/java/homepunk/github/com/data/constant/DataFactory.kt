package homepunk.github.com.data.constant

import android.content.Context
import homepunk.github.com.data.R
import homepunk.github.com.data.constant.model.DiscogsDiscoverSection

class DataFactory {
    companion object {
        fun getDiscogsDiscoverSectionList(context: Context): List<DiscogsDiscoverSection> {
            val dataList = mutableListOf<DiscogsDiscoverSection>()
            dataList.add(DiscogsDiscoverSection(0, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_RAP, context.resources.getStringArray(R.array.genres)[0]))
            dataList.add(DiscogsDiscoverSection(1, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ROCK, context.resources.getStringArray(R.array.genres)[1]))
            dataList.add(DiscogsDiscoverSection(2, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_REGGAE, context.resources.getStringArray(R.array.genres)[2]))
            dataList.add(DiscogsDiscoverSection(3, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ELECTRONIC, context.resources.getStringArray(R.array.genres)[3]))
            dataList.add(DiscogsDiscoverSection(4, Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE, context.resources.getStringArray(R.array.genres)[4]))
            return dataList
        }

        fun getSongkickArtistThumb(id: String?) = "https://images.sk-static.com/images/media/profile_images/artists/$id/huge_avatar"

        fun getSongkickArtistThumb(id: Long?) = "https://images.sk-static.com/images/media/profile_images/artists/$id/huge_avatar"
    }
}