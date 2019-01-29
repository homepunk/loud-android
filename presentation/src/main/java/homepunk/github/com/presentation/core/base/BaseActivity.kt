package homepunk.github.com.presentation.core.base

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.feature.mode.AppModeViewModel
import javax.inject.Inject


abstract class BaseActivity<BINDING : ViewDataBinding> : AppCompatActivity() {
    abstract fun getLayoutId(): Int
    abstract fun initViewModels()
    abstract fun init()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mDataBinding: BINDING
    lateinit var mAppModeViewModel: AppModeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        mAppModeViewModel = getViewModel(AppModeViewModel::class.java)
        mAppModeViewModel.run {
            currentAppModeModelLiveData.observe(this@BaseActivity, Observer {
                mDataBinding.setVariable(BR.appModeModel, it)

                currentAppModeTheme?.let { currentTheme ->
                    if (currentTheme != it.themeResId) {
                        recreate()
                    }
                }
            })
            subscribeOnModeChanges()
        }
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.navigationBarColor = ContextCompat.getColor(this, R.color.background)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutId())

        initViewModels()
        init()
    }

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()
        mAppModeViewModel.currentAppModeTheme?.run {
            theme.applyStyle(this, true)
        }
        return theme
    }

    fun <T : ViewModel> getViewModel(clazz: Class<T>) = ViewModelProviders.of(this, viewModelFactory)[clazz]

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
