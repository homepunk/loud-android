package homepunk.github.com.presentation.core.ext

import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import homepunk.github.com.presentation.util.SongkickUtil
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers




/**Created by Homepunk on 11.01.2019. **/
@BindingAdapter("imageUrl")
fun ImageView.bindImageUrl(imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        Single.just(Picasso.get())
                .subscribeOn(Schedulers.computation())
                .map { it.load(imageUrl) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Consumer {
                    it.into(this)
                }, Consumer { it.printStackTrace() })
    }
}

@BindingAdapter("songkickThumb")
fun ImageView.bindSongkickThumb(id: Long) {
    SongkickUtil.getSongkickArtistThumb(id).let { url ->
        Picasso.get()
                .load(url)
                .error(homepunk.github.com.presentation.R.drawable.ic_image_black_24dp)
                .into(this)
    }

}

@BindingAdapter("imageResId")
fun ImageView.bindDrawable(resId: Int) {
    setImageResource(resId)
}

@BindingAdapter("roundCornersImageResId")
fun ImageView.roundCornersImageResId(resId: Int) {
    val bitmap = drawableToBitmap(resources.getDrawable(resId))
    setImageBitmap(getRoundedCornerBitmap(bitmap, dpToPx(2f)))
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return bitmap
}

fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
    val output = Bitmap.createBitmap(bitmap.width, bitmap
            .height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(output)

    val color = -0xbdbdbe
    val paint = Paint()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val rectF = RectF(rect)
    val roundPx = pixels.toFloat()

    paint.isAntiAlias = true
    canvas.drawARGB(0, 0, 0, 0)
    paint.color = color
    canvas.drawRoundRect(rectF, roundPx, roundPx, paint)

    paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    canvas.drawBitmap(bitmap, rect, rect, paint)

    return output
}
@BindingAdapter("animatableImageResId")
fun ImageView.bindAnimatebleDrawable(resId: Int) {
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