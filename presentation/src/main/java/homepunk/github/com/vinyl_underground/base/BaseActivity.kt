package homepunk.github.com.vinyl_underground.base

import android.app.AppComponentFactory
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity<VM: BaseViweModel> : AppCompatActivity() {
    abstract fun getLayoutId(): Int

    abstract fun createViewModel(): VM

    abstract fun init()

    lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        mViewModel = createViewModel()

        init()
    }
}