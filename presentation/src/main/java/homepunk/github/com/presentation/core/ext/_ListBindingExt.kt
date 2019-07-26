package homepunk.github.com.presentation.core.ext

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingArrayAdapter


/**Created by Homepunk on 08.07.2019. **/
/*
@BindingAdapter(
        value = ["spinnerList, onItemClickListener"],
        requireAll = false)
fun <T> Spinner.bindSpinnerList(items: LiveData<List<T>>, listener: OnItemClickListener<T>?) {
    val adapter = SimpleBindingArrayAdapter(context, R.layout.layout_item_spinner_location, BR.model, items.value as MutableList<T>)
    adapter.onItemClickListener = listener
    setAdapter(adapter)
}
*/

@BindingAdapter("spinnerList")
fun <T> Spinner.bindSpinnerList(items: List<T>?) {
    if (items != null) {
        if (adapter != null) {
            (adapter as? SimpleBindingArrayAdapter<T>)?.let {
                it.items = items as MutableList<T>
                it.notifyDataSetChanged()
            }
        } else {
            val adapter = SimpleBindingArrayAdapter(context, items as MutableList<T>, R.layout.layout_item_spinner_location, R.layout.layout_item_spinner_dropdown_location)
            setAdapter(adapter)
        }
    }
}
