package homepunk.github.com.presentation.common.adapter.model

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean

/**Created by Homepunk on 18.03.2019. **/
abstract class ExpandableBindingParentModel<CHILD : ExpandableBindingChildModel>() : BaseObservable() {
    var children = ObservableArrayList<CHILD>()
    open var isParentExpanded = ObservableBoolean(false)

    constructor(children: List<CHILD>) : this() {
        this.children.addAll(children)
    }

    abstract fun getLayoutId(): Int

    abstract fun getBindingVariableId(): Int

}