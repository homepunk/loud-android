package homepunk.github.com.presentation.core.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<BINDING : ViewDataBinding> : Fragment() {
    abstract fun getLayoutResId(): Int
    abstract fun init()

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mDataBinding: BINDING

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        init()
        return mDataBinding.root
    }

    fun <T : ViewModel> getViewModel(clazz: Class<T>)  = ViewModelProviders.of(this, viewModelFactory)[clazz]

    fun getDimen(@DimenRes dimenId: Int) = resources.getDimension(dimenId)

    fun dLog(message: String) {
        Log.d(this.javaClass.canonicalName, message)
    }

    fun wLog(message: String) {
        Log.w(this.javaClass.canonicalName, message)
    }

    fun eLog(message: String) {
        Log.e(this.javaClass.canonicalName, message)
    }
}