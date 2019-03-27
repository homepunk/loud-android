package homepunk.github.com.presentation.feature.widget.parentrecycler

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 18.03.2019. **/
abstract class BindingParentModel(private var children: List<BindingChildModel>) : BaseObservable() {
    var isParentExpanded = ObservableBoolean(false)

    @Bindable
    fun getChildren() = children

    abstract fun getLayoutId(): Int

}