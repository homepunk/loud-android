package homepunk.github.com.presentation.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection
import homepunk.github.com.presentation.R

abstract class BaseFragment<VM : BaseViweModel, BINDING : ViewDataBinding> : Fragment() {
    abstract fun getLayoutResId(): Int

    abstract fun createViewModel(): VM

    abstract fun init()

    lateinit var mViewModel: VM
    lateinit var mDataBinding: BINDING

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        mViewModel = createViewModel()
        init()
        return mDataBinding.root
    }

    override fun onResume() {
        super.onResume()
    }

    fun getDimen(@DimenRes dimenId: Int) = resources.getDimension(dimenId)
}