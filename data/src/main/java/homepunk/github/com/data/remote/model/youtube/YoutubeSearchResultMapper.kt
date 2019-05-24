package homepunk.github.com.data.remote.model.youtube

import com.google.api.services.youtube.model.SearchResult
import homepunk.github.com.domain.model.youtube.YoutubeVideoPreview

/**Created by Homepunk on 23.05.2019. **/
object YoutubeSearchResultMapper {
    fun map(fromModel: SearchResult): YoutubeVideoPreview {
        val toModel = YoutubeVideoPreview()
        toModel.id = fromModel.id.videoId
        toModel.title = fromModel.snippet.title
        toModel.description = fromModel.snippet.description
        toModel.thumb = fromModel.snippet.thumbnails.high.url
        return toModel
    }
}