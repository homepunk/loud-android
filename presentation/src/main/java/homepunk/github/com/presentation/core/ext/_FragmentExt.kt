package homepunk.github.com.presentation.core.ext

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import homepunk.github.com.presentation.R

/**Created by Homepunk on 05.08.2019. **/

fun FragmentManager.replace(id: Int, fragment: Fragment) {
    beginTransaction()
            .setCustomAnimations(R.anim.translate_down_from_top, R.anim.translate_from_down_to_top, R.anim.translate_down_from_top, R.anim.translate_from_down_to_top)
            .replace(id, fragment)
            .addToBackStack(null)
            .commit()
}

fun FragmentManager.replace(id: Int, fragment: Fragment, enter: Int, exit: Int) {
    beginTransaction()
            .setCustomAnimations(enter, exit, enter, exit)
            .replace(id, fragment)
            .addToBackStack(null)
            .commit()
}