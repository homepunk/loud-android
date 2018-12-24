package homepunk.github.com.presentation.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<VM : BaseViweModel, BINDING : ViewDataBinding> : Fragment() {
    abstract fun getLayoutResId(): Int

    abstract fun createViewModel(): VM

    abstract fun init()

    abstract fun onRestart()

    open fun onPreInflate() { }

    lateinit var mViewModel: VM
    lateinit var mDataBinding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        onPreInflate()
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        mViewModel = createViewModel()
        init()
        return mDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        onRestart()
    }
}