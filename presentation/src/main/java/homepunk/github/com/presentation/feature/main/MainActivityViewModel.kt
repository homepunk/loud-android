package homepunk.github.com.presentation.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import homepunk.github.com.domain.interactor.AppModeInteractor
import homepunk.github.com.presentation.common.data.AppDataFactory
import homepunk.github.com.presentation.core.base.BaseViewModel
import homepunk.github.com.presentation.feature.discover.DiscoverHostFragment
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    @Inject lateinit var appDataFactory: AppDataFactory
    @Inject lateinit var appModeInteractor: AppModeInteractor

    var fragmentList = arrayListOf<Fragment>()

    var onPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                    navigation.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
//                    navigation.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
//                    navigation.onPageScrollStateChanged(state)
        }
    }


    override fun init() {
        fragmentList.add(DiscoverHostFragment())
        fragmentList.add(Fragment())
        fragmentList.add(Fragment())
    }
}

