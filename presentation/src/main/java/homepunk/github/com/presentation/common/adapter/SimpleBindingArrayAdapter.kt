package homepunk.github.com.presentation.common.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.core.listener.OnItemClickListener


/**Created by Homepunk on 08.07.2019. **/
class SimpleBindingArrayAdapter<T>(context: Context, var items: MutableList<T>, var resource: Int, var dropDownResource: Int? = null, var variableId: Int = BR.model) : ArrayAdapter<T>(context, resource, items) {

    var onItemClickListener: OnItemClickListener<T>? = null

    override fun getView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return createView((parent as Spinner).selectedItemPosition, recycledView, parent, resource)
    }

    override fun getDropDownView(position: Int, recycledView: View?, parent: ViewGroup): View {
        return createView(position, recycledView, parent, dropDownResource ?: resource)
    }

    private fun createView(position: Int, recycledView: View?, parent: ViewGroup, resource: Int): View {
        val item = getItem(position)!!
        val viewBinding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(context), resource, parent, false)
        viewBinding?.setVariable(variableId, item)

//        viewBinding?.root?.setOnClickListener {
//            onItemClickListener?.onClick(position, item)
//        }
        return viewBinding.root
    }

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): T? {
        return items[position]
    }
}

fun <T : ViewDataBinding> inflate(parent: ViewGroup, layoutId: Int) = DataBindingUtil.inflate<T>(LayoutInflater.from(parent.context), layoutId, parent, false)!!
