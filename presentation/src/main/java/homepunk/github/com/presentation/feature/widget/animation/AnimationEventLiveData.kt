package homepunk.github.com.presentation.feature.widget.animation

import androidx.lifecycle.LiveData
import homepunk.github.com.presentation.core.ext.toLiveData
import io.reactivex.BackpressureStrategy
import io.reactivex.subjects.PublishSubject

/**Created by Homepunk on 10.04.2019. **/
class AnimationEventLiveData private constructor() {
    private var scrollEventPublisher = PublishSubject.create<Int>()
    var scrollEventLivaData: LiveData<Int> = scrollEventPublisher.toLiveData(BackpressureStrategy.LATEST)

    fun onScrollEvent(value: Int) {
        scrollEventPublisher.onNext(value)
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