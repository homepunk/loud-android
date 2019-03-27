package homepunk.github.com.presentation.common.adapter.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 18.03.2019. **/
abstract class ExpandableBindingParentModel<CHILD : ExpandableBindingChildModel>(var parentChildren: List<CHILD>) : BaseObservable() {
    open var isParentExpanded = ObservableBoolean(false)

//    @Bindable

    abstract fun getLayoutId(): Int

    abstract fun getBindingVariableId(): Int

}