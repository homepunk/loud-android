package homepunk.github.com.presentation.common.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.databinding.LayoutItemParentLocationChildrenEventsBinding

/**Created by Homepunk on 23.01.2019. **/
class ExpandableRecyclerAdapter<CHILD> : RecyclerView.Adapter<ExpandableRecyclerAdapter<CHILD>.ViewHolder>(){
    var parentList = arrayListOf<Parent<CHILD>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutItemParentLocationChildrenEventsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = parentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var parent = parentList[position]
        holder.setUpParent(parent.title)
        holder.setUpChildren(parent.children)
    }

    inner class ViewHolder(var binding: LayoutItemParentLocationChildrenEventsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun setUpChildren(children: List<CHILD>) {
            binding.childrenLayout
        }

        fun setUpParent(title: String) {
            binding.parentLayout.sectionTitle = title
        }

    }

    class Parent<CHILD> {
        var title: String = ""
        var children = arrayListOf<CHILD>()
    }
}