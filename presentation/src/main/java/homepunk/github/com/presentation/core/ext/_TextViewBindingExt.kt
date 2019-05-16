package homepunk.github.com.presentation.core.ext

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.BindingAdapter
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.util.DateTimeUtil
import timber.log.Timber



/**Created by Homepunk on 18.01.2019. **/
@BindingAdapter("textRes")
fun TextView.bindTextRes(value: Int) {
    text = context.getString(value)
}

@BindingAdapter("monthForInt")
fun TextView.bindMonthForInt(value: Int) {
    text = DateTimeUtil.getMonthForInt(value)
}

@BindingAdapter("link")
fun TextView.bindUrl(value: String?) {
    Timber.w("bindUrl $value")
    val visitUrlText = context.getString(R.string.visit_website)
    text = SpannableString(visitUrlText).apply {
        if (value != null) {
            setLinkSpan(visitUrlText, value)
        }
    }
}

fun SpannableString.setLinkSpan(text: String, url: String) {
    val textIndex = this.indexOf(text)
    setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Intent(Intent.ACTION_VIEW)
                            .apply { data = Uri.parse(url) }
                            .also { startActivity(widget.context, it, Bundle.EMPTY) }
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false    // this line removes underline
                }
            },
            textIndex,
            textIndex + text.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}
