package homepunk.github.com.presentation.core.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


abstract class BaseBindingActivity<VM : BaseViweModel, BINDING : ViewDataBinding> : AppCompatActivity(){
    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): VM

    abstract fun init()

    lateinit var mViewModel: VM
    lateinit var mDataBinding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = createViewModel()

        init()
    }

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
