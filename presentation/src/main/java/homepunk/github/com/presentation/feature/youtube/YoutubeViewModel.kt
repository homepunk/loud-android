package homepunk.github.com.presentation.feature.youtube

import androidx.databinding.ObservableArrayList
import homepunk.github.com.domain.interactor.YoutubeInteractor
import homepunk.github.com.domain.model.youtube.YoutubeVideo
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.core.ext.asList
import io.reactivex.functions.Consumer
import javax.inject.Inject

/**Created by Homepunk on 24.05.2019. **/
class YoutubeViewModel @Inject constructor(var youtubeInteractor: YoutubeInteractor) : BaseViewModel() {
    var videoList = ObservableArrayList<YoutubeVideo>()

    fun setUpQuery(query: String) {
        setUpQuery(query.asList())
    }

    fun setUpQuery(queries: List<String>) {
        videoList.clear()
        compositeDisposable.add(youtubeInteractor.getVideoList(queries)
                .doOnError { it.printStackTrace() }
                .toList()
                .subscribe(Consumer { videoList.addAll(it) }))
    }
}

