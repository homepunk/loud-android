package homepunk.github.com.presentation.core.ext

import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

/**Created by Homepunk on 29.05.2019. **/

@BindingAdapter(requireAll = false, value = [
    "onInitializedListener",
    "initIf"
])
fun FrameLayout.setUpPlayerView(onInitializedListener: YouTubePlayer.OnInitializedListener, init: Boolean) {
    if (init) {
        val fragment = YouTubePlayerSupportFragment.newInstance()
        fragment.initialize("AIzaSyBHmQY7e766b7Kcgtzw1dohgFjeadDFwU8", onInitializedListener)

    }
}