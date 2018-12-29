package homepunk.github.com.presentation.core.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import javax.inject.Inject


abstract class BaseBindingActivity<BINDING : ViewDataBinding> : AppCompatActivity(){
    abstract fun getLayoutId(): Int
    abstract fun initViewModels()
    abstract fun init()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mDataBinding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())

        initViewModels()
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
