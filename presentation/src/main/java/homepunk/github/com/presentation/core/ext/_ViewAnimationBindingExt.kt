package homepunk.github.com.presentation.core.ext

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateInterpolator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.core.wrapper.AnimationListenerWrapper
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.feature.widget.animation.ReverseInterpolator
import homepunk.github.com.presentation.util.DimensionUtil


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
                    translationX = if (i == 0) {
                        DimensionUtil.screenWidth.toFloat()
                    } else {
                        value * koef
                    }
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

@BindingAdapter(
        requireAll = false,
        value = [
            "isVisible",
            "itemAnimationHorizontall",
            "startDelay"])
fun RecyclerView.itemAnimationHorizontall(newValue: Boolean, itemAnimationEnabled: Boolean, startDelay: Int) {
    if (itemAnimationEnabled) {
        var isRerversed = false

        val translationAnimator = ValueAnimator.ofFloat(DimensionUtil.screenWidth.toFloat(), 0f)
        translationAnimator.duration = 520L
        translationAnimator.addListener(object : AnimatorListenerWrapper() {
            override fun onAnimationStart(animation: Animator?) {
                if (!isRerversed) {
                    visibility = VISIBLE
                }
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                if (isRerversed) {
                    visibility = GONE
                }
            }
        })
        translationAnimator.interpolator = AnticipateInterpolator()
        translationAnimator.startDelay = startDelay.toLong()
        translationAnimator.addUpdateListener { translationX = (it.animatedValue as Float) }
        if (!newValue) {
            isRerversed = true
            translationAnimator.reverse()
        } else {
            isRerversed = false
            translationAnimator.start()
        }
    }
}

@BindingAdapter("bind:onParentChildClickListener")
fun View.SetOnClick(listener: View.OnClickListener?) {
    listener?.let {
        setOnClickListener(it)
    }
}
