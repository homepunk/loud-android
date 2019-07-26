package homepunk.github.com.presentation.feature.youtube

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import homepunk.github.com.domain.interactor.YoutubeInteractor
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.asList
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.youtube.model.YoutubeVideoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**Created by Homepunk on 24.05.2019. **/
class YoutubeViewModel @Inject constructor(var youtubeInteractor: YoutubeInteractor) : BaseViewModel() {
    var query = ObservableField<String>()
    var videoList = ObservableArrayList<YoutubeVideoModel>()

    var player: YouTubePlayer? = null
    var playPosition = ObservableInt(-1)
    var playVideoModel: YoutubeVideoModel? = null
    var onPlayClickListener = object : OnItemClickListener<YoutubeVideoModel> {
        override fun onClick(position: Int, item: YoutubeVideoModel) {
            if (item.isPlayed.swap()) {
                playPosition.set(position)
                player?.cueVideo(item.youtubeVideo.id)
                player?.play()
                playVideoModel= item
            } else {
                playPosition.set(-1)
                player?.pause()
                playVideoModel = null
            }
        }
    }

    var onInitializedListener = object : YouTubePlayer.OnInitializedListener {
        override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, player: YouTubePlayer?, wasRestored: Boolean) {
            wLog("onInitializationSuccess")
            this@YoutubeViewModel.player = player?.apply {
                setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS)
                setShowFullscreenButton(false)
            }
            if (!wasRestored) {
//                player?.cueVideo(playVideoModel.value!!.youtubeVideo.id)
//                player?.play()
            }
        }

        override fun onInitializationFailure(provider: YouTubePlayer.Provider?, result: YouTubeInitializationResult?) {
            wLog(message = "onInitializationFailure ${result?.name}")
        }
    }
    fun setUpQuery(query: String) {
        this.query.set(query)
        setUpQuery(query.asList())
    }

    fun setUpQuery(queries: List<String>) {
        videoList.clear()
        subscriptions.add(youtubeInteractor.getVideoList(queries)
                .doOnError { it.printStackTrace() }
                .map { YoutubeVideoModel(it) }
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer { videoList.addAllToEmptyList(it) }))
    }
}

