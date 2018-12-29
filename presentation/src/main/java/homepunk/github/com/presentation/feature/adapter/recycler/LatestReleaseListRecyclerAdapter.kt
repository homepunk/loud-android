package homepunk.github.com.presentation.feature.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import homepunk.github.com.domain.model.search.SearchResult
import homepunk.github.com.presentation.databinding.ListItemLatestReleaseBinding
import homepunk.github.com.presentation.feature.adapter.recycler.LatestReleaseListRecyclerAdapter.ViewHolder

class LatestReleaseListRecyclerAdapter : RecyclerView.Adapter<ViewHolder>() {
    var items: List<SearchResult> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemLatestReleaseBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ListItemLatestReleaseBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchResult) {
            with(binding) {
                title.text = item.title
                Picasso.get()
                        .load(item.thumb)
                        .into(thumb)
            }
        }
    }
}