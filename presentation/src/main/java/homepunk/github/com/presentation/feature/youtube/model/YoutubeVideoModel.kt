package homepunk.github.com.presentation.feature.youtube.model

import androidx.databinding.ObservableBoolean
import homepunk.github.com.domain.model.youtube.YoutubeVideo

/**Created by Homepunk on 28.05.2019. **/
class YoutubeVideoModel(var youtubeVideo: YoutubeVideo) {
    var isPlayed = ObservableBoolean(false)

}