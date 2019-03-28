package homepunk.github.com.presentation.core.ext

import android.animation.ValueAnimator
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.core.wrapper.AnimationListenerWrapper
import homepunk.github.com.presentation.feature.widget.animation.ReverseInterpolator

/**Created by Homepunk on 31.01.2019. **/


@BindingAdapter(requireAll = false, value = ["animationResId", "visibility", "animationStartOffset"])
fun ViewGroup.bindAnimationResId(resId: Int, visibilityValue: Int, startOffset: Int) {
    if (resId != 0) {
        val set: AnimationSet = AnimationUtils.loadAnimation(context, resId) as AnimationSet
        if (visibilityValue == View.GONE) {
            set.interpolator = AnticipateInterpolator()
        } else {
            set.startOffset = startOffset.toLong()
            set.interpolator = ReverseInterpolator(set.interpolator)
        }
        set.animations[0]?.setAnimationListener(object : AnimationListenerWrapper() {
            override fun onAnimationStart(animation: Animation?) {
                if (visibilityValue != View.GONE) {
                    visibility = visibilityValue
                }
            }

            override fun onAnimationEnd(animation: Animation?) {
                if (visibilityValue == View.GONE) {
                    visibility = visibilityValue
                }
            }
        })
        startAnimation(set)
    } else {
        visibility = visibilityValue
    }
}

@BindingAdapter(requireAll = false, value = ["animationResId", "text"])
fun TextView.bindAnimationResId(resId: Int, text: String) {
    if (resId != 0 && getText().isNotEmpty()) {
        val set = AnimationUtils.loadAnimation(context, resId) as AnimationSet
        set.interpolator = AnticipateInterpolator()
        set.animations[0]?.setAnimationListener(object : AnimationListenerWrapper() {
            override fun onAnimationRepeat(animation: Animation?) {
                setText(text)
            }
        })
        startAnimation(set)
    } else {
        setText(text)
    }
}

@BindingAdapter(
        requireAll = false,
        value = [
            "visibility",
            "itemAnimationEnabled"])
fun RecyclerView.bindItemAnimation(newValue: Int, itemAnimationEnabled: Boolean) {
    if (itemAnimationEnabled) {
        fun updateChildren(value: Float) {
            var koef = 1.0f
            for (i in 0 until childCount) {
                koef += 0.3f
                getChildAt(i)?.apply {
                    translationY = value * koef
                }
            }
        }

        val translationAnimator = ValueAnimator.ofFloat(80f, 0f)
        translationAnimator.duration = 520L
        translationAnimator.interpolator = AnticipateInterpolator()
        translationAnimator.addUpdateListener { updateChildren(it.animatedValue as Float) }
        if (newValue == GONE) {
            translationAnimator.reverse()
        } else {
            translationAnimator.startDelay = 200
            translationAnimator.start()
        }
    } else {
        visibility = newValue
    }
}

@BindingAdapter("bind:onClickListener")
fun View.SetOnClick(listener: View.OnClickListener?) {
    listener?.let {
        setOnClickListener(it)
    }
}
