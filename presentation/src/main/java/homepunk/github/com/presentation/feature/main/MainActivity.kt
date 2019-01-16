package homepunk.github.com.presentation.feature.main;

import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.adapter.SimpleViewPagerAdapter
import homepunk.github.com.presentation.core.base.BaseBindingActivity
import homepunk.github.com.presentation.core.wrapper.BottomSheetCallbackWrapper
import homepunk.github.com.presentation.databinding.ActivityMainBinding
import homepunk.github.com.presentation.databinding.LayoutBottomMenuBinding
import homepunk.github.com.presentation.feature.main.discover.DiscoverFragment
import homepunk.github.com.presentation.feature.main.event.EventFragment
import homepunk.github.com.presentation.feature.recognition.CameraRecognitionFragment
import homepunk.github.com.presentation.widget.searchview.MaterialSearchToLine
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException


class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    private lateinit var mMainViewModel: MainActivityViewModel

    private lateinit var discoverFragment: DiscoverFragment
    private lateinit var eventFragment: EventFragment
    private lateinit var cameraFragment: CameraRecognitionFragment

    override fun getLayoutId() = R.layout.activity_main

    override fun initViewModels() {
        mMainViewModel = MainActivityViewModel()
    }

    override fun init() {
        setUpViewPager()
        setUpBottomMenu()
    }

    private lateinit var bottomMenuBinding: LayoutBottomMenuBinding

    private fun setUpBottomMenu() {
        val bottomMenu = mDataBinding.container.findViewById<LinearLayout>(R.id.bottom_menu_container)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomMenu)
        bottomMenuBinding = DataBindingUtil.findBinding(bottomMenu)!!
        bottomMenuBinding.layoutToolbar?.let {
            it.materialSearchToLine.run {
                setOnClickSearchListener {
                    if (mState == MaterialSearchToLine.State.LINE) {
                        transformToSearch()
                    } else {
                        transformToLine()
                    }
                }

                setOnChangeListener { state ->
                    when (state) {
                        MaterialSearchToLine.State.LINE -> {
                            it.materialEditTextLine.visibility = VISIBLE
                            it.profileImage.visibility = GONE
                            it.materialEditTextLine.isFocusable = true
                            it.materialEditTextLine.isFocusableInTouchMode = true
                            it.materialEditTextLine.requestFocus()
                            it.materialEditTextLine.startAnimation(AlphaAnimation(0.0f, 1.0f)
                                    .apply {
                                        duration = 300
                                        fillAfter = true
                                        setAnimationListener(object: Animation.AnimationListener {
                                            override fun onAnimationRepeat(animation: Animation?) {

                                            }

                                            override fun onAnimationEnd(animation: Animation?) {
                                            }

                                            override fun onAnimationStart(animation: Animation?) {
                                                it.materialEditTextLine.setHintTextColor(Color.parseColor("#a4a4a4"))
                                            }
                                        })
                                    })
                            it.materialClearButton.startAnimation(AlphaAnimation(0.0f, 1.0f)
                                    .apply {
                                        duration = 300
                                        fillAfter = true
                                        setAnimationListener(object: Animation.AnimationListener {
                                            override fun onAnimationRepeat(animation: Animation?) {

                                            }

                                            override fun onAnimationEnd(animation: Animation?) {
                                            }

                                            override fun onAnimationStart(animation: Animation?) {
                                                it.materialClearButton.background = getDrawableFromXml(resources, R.drawable.ic_close_gray)
                                                it.materialClearButton.visibility = VISIBLE
                                            }
                                        })
                                    })
                        }
                        MaterialSearchToLine.State.SEARCH -> {
                            it.profileImage.visibility = VISIBLE
                            it.materialClearButton.visibility = GONE
                            it.materialEditTextLine.visibility = GONE
                        }
                    }
                }
            }
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetCallbackWrapper() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                mDataBinding.pagerOverlay.alpha = slideOffset * 0.7f
            }
        })
    }

    fun getDrawableFromXml(resources: Resources, id: Int): Drawable? {
        var drawable: Drawable? = null
        try {
            drawable = Drawable.createFromXml(resources, resources.getXml(id))
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return drawable
    }

    override fun onBackPressed() {
        if (bottomMenuBinding.layoutToolbar.materialSearchToLine.mState == MaterialSearchToLine.State.LINE) {
            bottomMenuBinding.layoutToolbar.materialSearchToLine.transformToSearch()
        } else {
            super.onBackPressed()
        }

    }
    private fun setUpViewPager() {
        val adapter = SimpleViewPagerAdapter(supportFragmentManager)
        discoverFragment = DiscoverFragment()
        eventFragment = EventFragment()
        val homeFragment3 = DiscoverFragment()
        cameraFragment = CameraRecognitionFragment()
        adapter.addFragment(discoverFragment)
        adapter.addFragment(eventFragment)
        adapter.addFragment(homeFragment3)
        mDataBinding.run {
            viewPager.adapter = adapter
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    navigation.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    navigation.onPageSelected(position)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    navigation.onPageScrollStateChanged(state)
                }
            })
        }
    }
}
