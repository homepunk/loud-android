package homepunk.github.com.presentation.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

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