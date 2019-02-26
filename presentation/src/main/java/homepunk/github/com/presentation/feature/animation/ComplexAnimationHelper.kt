package homepunk.github.com.presentation.feature.animation

import android.view.animation.Animation
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**Created by Homepunk on 01.02.2019. **/
object ComplexAnimationHelper {
    private var animationsMap = mutableMapOf<Int, Animation>()
    private var animationsPublisher = PublishSubject.create<Animation>()

    fun putAnimation(order: Int, animation: Animation) {
        animationsMap[order] = animation
    }

    fun reset() {
        animationsMap.clear()
    }

    fun getAnimationObservable(): Observable<Animation> = animationsPublisher
}