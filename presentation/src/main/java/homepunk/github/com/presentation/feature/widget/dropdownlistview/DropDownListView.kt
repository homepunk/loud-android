package homepunk.github.com.presentation.feature.widget.dropdownlistview

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.core.ext.bindOnItemClickListener
import homepunk.github.com.presentation.core.listener.OnItemClickListener

/**Created by Homepunk on 09.07.2019. **/
class DropDownListView<T> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : FrameLayout(context, attrs, defStyleAttr) {

    private val dialog: Dialog by lazy {
        createDialog(context)
    }
    private lateinit var recyclerAdapter: SimpleBindingRecyclerAdapter<T>

    private var itemList: List<T>? = null
    private var currentItem: T? = null
    private var currentPosition: Int = -1


    private var dropDownResourceId: Int
    private var dropDownResourceVarId: Int

    private var resourceId: Int
    private var resourceVarId: Int

    private var resourceViewBinding: ViewDataBinding

    var onDropDownClickListener: OnDropDownClickListener<T>? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.DropDownListView, 0, 0)
        try {
            resourceId = typedArray.getResourceId(R.styleable.DropDownListView_layoutResource, 0)
            resourceVarId = typedArray.getInteger(R.styleable.DropDownListView_layoutResourceVariableId, BR.model)
            dropDownResourceId = typedArray.getResourceId(R.styleable.DropDownListView_dropDownLayoutResource, 0)
            dropDownResourceVarId = typedArray.getInteger(R.styleable.DropDownListView_dropDownLayoutResourceVariableId, BR.model)
        } finally {
            typedArray.recycle()
        }

        resourceViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resourceId, this, true)
        with(resourceViewBinding) {
            root.setOnClickListener {
                if (dialog.isShowing) {
                    dialog.hide()
                } else {
                    itemList?.let {
                        recyclerAdapter.setItemList(it)
                        recyclerAdapter.notifyDataSetChanged()
                    }
                    dialog.show()
                }
            }
        }
    }


    fun setItemList(itemList: List<T>) {
        this.itemList = itemList
        this.currentItem = null
        this.currentPosition = -1
    }

    fun setItemList(position: Int, itemList: List<T>) {
        if (!itemList.isNullOrEmpty()) {
            this.itemList = itemList
            updateSelectedItem(position)
        }
    }

    fun setCurrentItem(position: Int) {
        if (itemList != null &&
                position < itemList!!.size) {
            updateSelectedItem(position)
        }
    }

    private fun updateSelectedItem(position: Int) {
        itemList?.let {
            if (currentItem != it[position]) {
                currentItem = it[position]
                currentPosition = position
                resourceViewBinding.setVariable(resourceVarId, currentItem!!)
            }
        }
    }

    private fun createDialog(context: Context): Dialog {
        val d = Dialog(context)
        d.setContentView(R.layout.custom_layout_recycler_dialog)
        val recycler = d.findViewById<RecyclerView>(R.id.recycler)
        recyclerAdapter = SimpleBindingRecyclerAdapter<T>(dropDownResourceId, dropDownResourceVarId)
        recycler.adapter = recyclerAdapter

        recycler.bindOnItemClickListener(object : OnItemClickListener<T> {
            override fun onClick(position: Int, item: T) {
                if (currentPosition != position) {
                    recycler.getChildAt(currentPosition)?.isSelected = false
                    recycler.getChildAt(position)?.isSelected = true
                    currentPosition = position
                    onDropDownClickListener?.onClick(position, item)
                }
            }
        })
        d.setOnShowListener {
            recycler.getChildAt(currentPosition)?.performClick()
        }
        d.setOnDismissListener {
            updateSelectedItem(currentPosition)
            onDropDownClickListener?.onSelect(currentPosition, currentItem!!)
        }
        d.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return d
    }
}
