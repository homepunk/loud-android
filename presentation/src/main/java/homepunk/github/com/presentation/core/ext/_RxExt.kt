package homepunk.github.com.presentation.core.ext

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import io.reactivex.*
import timber.log.Timber


/**Created by Homepunk on 18.01.2019. **/
fun <T> Flowable<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this)
}

fun <T> Observable<T>.toLiveData(backPressureStrategy: BackpressureStrategy): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable(backPressureStrategy))
}

fun <T> Single<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Maybe<T>.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Completable.toLiveData(): LiveData<T> {
    return LiveDataReactiveStreams.fromPublisher(this.toFlowable())
}

fun <T> Observable<T>.useIfEmpty(dataSource: Observable<T>): Observable<T> {
    return defaultIfEmpty(null)
            .doOnNext { Timber.w("NEXT VALUE: IS NULL ${it == null}")}
            .flatMap {item ->
                if (item == null)
                    dataSource
                else
                    this
            }
}

/*fun <T> doOnFirst(consumer: Consumer<in T>): FlowableTransformer<T, T> {
    return Flowable.defer<T> {
            val first = AtomicBoolean(true)
            f.doOnNext({ t ->
                if (first.compareAndSet(true, false)) {
                    consumer.accept(t)
                }
            })
}*/

//fun <T> applySchedulers(): Transformer<T, T> {
//    return { observable ->
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//    }
//}