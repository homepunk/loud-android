package homepunk.github.com.presentation.core.ext

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.SpecificFieldClickListener

/**Created by Homepunk on 27.05.2019. **/

@BindingAdapter("onFieldClickListener")
fun <T> RecyclerView.bindOnFieldClickListener(listener: SpecificFieldClickListener<T>?) {
    (adapter as SimpleBindingRecyclerAdapter<T>).onSpecificFieldClickListener = listener
}

@BindingAdapter("onItemClickListener")
fun <T> RecyclerView.bindOnItemClickListener(listener: OnItemClickListener<T>?) {
    (adapter as SimpleBindingRecyclerAdapter<T>).onItemClickListener = listener
}