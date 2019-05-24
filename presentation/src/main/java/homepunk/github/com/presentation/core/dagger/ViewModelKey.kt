package homepunk.github.com.presentation.common.dagger

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

/**Created by Homepunk on 27.12.2018. **/
@MustBeDocumented
@Target(FUNCTION,
        PROPERTY_GETTER,
        PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)