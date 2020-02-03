package homepunk.github.com.presentation.feature.widget.timeline

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.StickyAdapter
import homepunk.github.com.presentation.core.ext.getColor
import homepunk.github.com.presentation.feature.widget.timeline.model.TimelineEventModel
import homepunk.github.com.presentation.util.LayoutUtil.inflateBindingLayout
import timber.log.Timber

/**Created by Homepunk on 22.07.2019. **/
class TimelineStickyDateRecyclerAdapter<T>(private val defaultLayoutId: Int,
                                           private val dateLayoutId: Int,
                                           private var items: ObservableArrayList<TimelineEventModel<T>>,
                                           private val variableId: Int = BR.model)
    : StickyAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder>() {

    init {
        items.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>?>() {
            override fun onChanged(sender: ObservableList<T>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyItemMoved(fromPosition, toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }
        })
    }

    var onNewDateListener: OnNewDateListener<TimelineEventModel<T>>? = null

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TimelineEventModel.ITEM)
            ItemViewHolder<T>(inflateBindingLayout(parent, defaultLayoutId), variableId)
        else
            DateViewHolder(LayoutInflater.from(parent.context).inflate(dateLayoutId, parent, false))

    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return createViewHolder(parent, TimelineEventModel.HEADER)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder, headerPosition: Int) {
        Timber.w("onBindHeaderViewHolder $headerPosition")
        val model = items[headerPosition]
        onNewDateListener?.onNewDate(model)
        (holder as DateViewHolder).bind(model.getDay(), true)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        items[position].let {
            if (it.itemType == TimelineEventModel.ITEM) {
                (holder as ItemViewHolder<T>).bind(it.item)
            } else {
                (holder as DateViewHolder).bind(it.getDay(), false)
            }

        }
    }

    override fun getHeaderPositionForItem(itemPosition: Int): Int {
        return items[itemPosition].dayIndex
    }

  /*  fun setFavoriteCountries(items: List<TimelineEventModel<T>>, getHeaderDateText: (TimelineEventModel<T>) -> String) {
        this.items = items
        this.getHeaderDateText = getHeaderDateText
        notifyDataSetChanged()
    }

*/
    class ItemViewHolder<ITEM>(val binding: ViewDataBinding, val variableId: Int) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ITEM) {
            with(binding) {
                setVariable(variableId, item)
            }
        }
    }

    class DateViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val day: TextView by lazy { root.findViewById<TextView>(R.id.day) }

        fun bind(text: String, highlight: Boolean) {
            day.text = text
            day.setTextColor(itemView.getColor(if (highlight) R.color.colorAccent else R.color.text))
        }
    }

    interface OnNewDateListener<T> {
        fun onNewDate(item: T)
    }
}