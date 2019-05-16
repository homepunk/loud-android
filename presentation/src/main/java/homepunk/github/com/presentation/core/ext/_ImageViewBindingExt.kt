package homepunk.github.com.presentation.core.ext

import android.graphics.drawable.Animatable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.util.SongkickUtil
import timber.log.Timber

/**Created by Homepunk on 11.01.2019. **/
@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Picasso.get()
                .load(imageUrl)
                .error(R.drawable.ic_image_black_24dp)
                .into(this)
    }
}

@BindingAdapter("songkickThumb")
fun ImageView.bindSongkickThumb(id: Long) {
    SongkickUtil.getSongkickArtistThumb(id).let { url ->
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_image_black_24dp)
                .into(this)
    }

}

@BindingAdapter("animatableImageResId")
fun ImageView.bindAnimatebleDrawable(resId: Int) {
    Timber.w("bindAnimatebleDrawable")
//    if (drawable == null) {
//        setImageResource(resId)
//    } else {
    setImageResource(resId)
    drawable?.run {
        this as Animatable
    }?.let {
        it.start()
    }
//    }
}

@BindingAdapter("imageResId")
fun ImageView.bindDrawable(resId: Int) {
    setImageResource(resId)
}