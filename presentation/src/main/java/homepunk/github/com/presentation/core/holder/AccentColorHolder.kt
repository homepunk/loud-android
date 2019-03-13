package homepunk.github.com.presentation.core.holder

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**Created by Homepunk on 13.03.2019. **/
class AccentColorHolder private constructor() {
    private var publisher: PublishSubject<Int> = PublishSubject.create()
    private var color: Int = 0

    var colorObservable: Observable<Int> = publisher

    fun update(color: Int) {
        publisher.onNext(color)
        this.color = color
    }

    companion object {
        val instance = AccentColorHolder()
    }
}