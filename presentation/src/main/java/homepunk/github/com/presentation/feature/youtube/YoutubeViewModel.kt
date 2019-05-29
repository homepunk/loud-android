package homepunk.github.com.presentation.feature.youtube

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import homepunk.github.com.domain.interactor.YoutubeInteractor
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.asList
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.feature.youtube.model.YoutubeVideoModel
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**Created by Homepunk on 24.05.2019. **/
class YoutubeViewModel @Inject constructor(var youtubeInteractor: YoutubeInteractor) : BaseViewModel() {
    var query = ObservableField<String>()
    var videoList = ObservableArrayList<YoutubeVideoModel>()

    var playPosition = ObservableInt(-1)
    var onPlayClickListener = object : OnItemClickListener<YoutubeVideoModel> {
        override fun onClick(position: Int, item: YoutubeVideoModel) {
            if (item.isPlayed.swap()) {
                playPosition.set(position)
            } else {
                playPosition.set(-1)
            }
        }
    }

    fun setUpQuery(query: String) {
        this.query.set(query)
        setUpQuery(query.asList())
    }

    fun setUpQuery(queries: List<String>) {
        videoList.clear()
        compositeDisposable.add(youtubeInteractor.getVideoList(queries)
                .doOnError { it.printStackTrace() }
                .map { YoutubeVideoModel(it) }
                .toList()
                .subscribe(Consumer { videoList.addAllToEmptyList(it) }))
    }
}

