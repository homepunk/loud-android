package homepunk.github.com.data.constant

import android.content.Context
import homepunk.github.com.data.R
import homepunk.github.com.data.constant.model.LatestReleaseTypeModel

class DataFactory {
    companion object {
        fun getLatestReleaseSectionList(context: Context): List<LatestReleaseTypeModel> {
            val dataList = mutableListOf<LatestReleaseTypeModel>()
            dataList.add(LatestReleaseTypeModel(0, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_RAP, context.resources.getStringArray(R.array.genres)[0]))
            dataList.add(LatestReleaseTypeModel(1, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ROCK, context.resources.getStringArray(R.array.genres)[1]))
            dataList.add(LatestReleaseTypeModel(2, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_REGGAE, context.resources.getStringArray(R.array.genres)[2]))
            dataList.add(LatestReleaseTypeModel(3, Constant.DISCOGS.LATEST_RELEASE_GENRE_TYPE_ELECTRONIC, context.resources.getStringArray(R.array.genres)[3]))
            dataList.add(LatestReleaseTypeModel(4, Constant.DISCOGS.LATEST_RELEASE_TYPE_RELEASE, context.resources.getStringArray(R.array.genres)[4]))
            return dataList
        }
    }
}