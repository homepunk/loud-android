package homepunk.github.com.presentation.feature.widget.animation

import androidx.lifecycle.LiveData
import homepunk.github.com.presentation.core.ext.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject

/**Created by Homepunk on 10.04.2019. **/
class AnimationEventLiveData private constructor() {
    private var scrollEventPublisher = PublishSubject.create<ScrollEvent>()
    var scrollEventLivaData: LiveData<ScrollEvent> = scrollEventPublisher.toLiveData(BackpressureStrategy.LATEST)
    var isScrollToPosition = false

    fun onScrollEvent(value: ScrollEvent) {
        scrollEventPublisher.onNext(value.apply {
            isScrollToPosition = this@AnimationEventLiveData.isScrollToPosition
        })
    }


    companion object {
        private var instance: AnimationEventLiveData? = null

        fun getInstance(): AnimationEventLiveData {
            if (instance == null) {
                instance = AnimationEventLiveData()
            }
            return instance!!
        }
    }
}

data class ScrollEvent(var scrollY: Int = 0,
                       var contentHeight: Int = 0,
                       var isScrollToPosition: Boolean = false)
