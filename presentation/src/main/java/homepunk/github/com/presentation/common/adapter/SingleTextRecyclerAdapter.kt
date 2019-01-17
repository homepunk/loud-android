package homepunk.github.com.presentation.common.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.R

/**Created by Homepunk on 16.01.2019. **/

class SingleTextRecyclerAdapter(val dataList: List<String>) : RecyclerView.Adapter<SingleTextRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_signle_text, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    inner class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
        fun bind(title: String) {
            val textView = root.findViewById<TextView>(R.id.text)
            textView.text = title
        }
    }

}