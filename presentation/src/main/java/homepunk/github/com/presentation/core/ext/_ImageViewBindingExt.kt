package homepunk.github.com.presentation.core.ext

import android.graphics.drawable.Animatable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import homepunk.github.com.presentation.R

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

@BindingAdapter("animatableImageResId")
fun ImageView.bindAnimatebleDrawable(resId: Int) {
    setImageResource(resId)
    drawable?.run {
        this as Animatable
    }?.let {
        it.start()
    }
}
@BindingAdapter("imageResId")
fun ImageView.bindDrawable(resId: Int) {
    setImageResource(resId)
}