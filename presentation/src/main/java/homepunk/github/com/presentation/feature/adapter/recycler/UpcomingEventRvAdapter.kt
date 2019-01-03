package homepunk.github.com.presentation.feature.adapter.recycler

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import homepunk.github.com.data.constant.DataFactory
import homepunk.github.com.domain.model.discogs.search.SearchResult
import homepunk.github.com.domain.model.songkick.SongkickEvent
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.databinding.LayoutItemUpcomingEventBinding
import javax.xml.transform.TransformerFactory

class UpcomingEventRvAdapter : RecyclerView.Adapter<UpcomingEventRvAdapter.ViewHolder>() {
    var items: List<SongkickEvent> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutItemUpcomingEventBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: LayoutItemUpcomingEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SongkickEvent) {
            with(binding) {
                if (!item.performance.isNullOrEmpty()) {
                    item.performance[0].artist?.let {
                        Picasso.get()
                                .load(DataFactory.getSongkickArtistThumb(it.id.toString()))
                                .into(thumb)
                    }
                    title.text = item.displayName
                }

            }
        }
    }
}