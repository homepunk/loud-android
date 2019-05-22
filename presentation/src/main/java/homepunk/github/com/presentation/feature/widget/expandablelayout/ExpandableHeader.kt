package homepunk.github.com.presentation.feature.widget.expandablelayout

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableBoolean
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.core.ext.swap
import homepunk.github.com.presentation.core.wrapper.AnimatorListenerWrapper
import homepunk.github.com.presentation.databinding.CustomExpandableLayoutBinding

/**Created by Homepunk on 06.05.2019. **/
class ExpandableHeader @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private val isExpanded = ObservableBoolean()
    private var isClicked = false

    private var binding: CustomExpandableLayoutBinding

    init {
        orientation = VERTICAL

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableHeader, 0, 0)

        val title: String?
        val headerHorizontalPadding: Int
        val headerTitleStartMargin: Int
        val headerTint: Int
        try {
            title = typedArray.getString(R.styleable.ExpandableHeader_el_title)
            headerHorizontalPadding = typedArray.getDimensionPixelSize(R.styleable.ExpandableHeader_el_headerHorizontalPadding, 0)
            headerTitleStartMargin = typedArray.getDimensionPixelSize(R.styleable.ExpandableHeader_el_headerTitleStartMargin, dpToPx(10f))
            isExpanded.set(typedArray.getBoolean(R.styleable.ExpandableHeader_el_expanded, false))
            headerTint = typedArray.getColor(R.styleable.ExpandableHeader_el_headerTint, ContextCompat.getColor(context, R.color.colorAccent))
        } finally {
            typedArray.recycle()
        }

//        LayoutInflater.from(context).inflate(R.layout.custom_expandable_layout, this, true)
        binding = CustomExpandableLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        binding.isExpanded = isExpanded
        binding.headerTint = headerTint
        binding.btnExpand.isPressed = !isExpanded.get()
        (binding.title.layoutParams as MarginLayoutParams).marginStart = headerTitleStartMargin

        title?.let {
            binding.title.text = it
        }

        binding.root.setPadding(headerHorizontalPadding, 0, headerHorizontalPadding, 0)
        binding.root.setOnClickListener {
            isClicked = true
            expand(isExpanded.swap())
        }
    }

    fun setHeaderTitle(title: String) {
        binding.title.text = title
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (!isClicked) {
            val height = if (!isExpanded.get())
                getHeaderHeight()
            else
                measuredHeight
            setMeasuredDimension(measuredWidth, height)
        }
    }

    fun expand(expand: Boolean) {
        measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val minHeight = getHeaderHeight()
        val va: ValueAnimator?
        if (expand) {
            va = ValueAnimator.ofInt(minHeight, measuredHeight)
            va.interpolator = AccelerateInterpolator()
//            visibility = View.VISIBLE
        } else {
            va = ValueAnimator.ofInt(measuredHeight, minHeight)
            va.interpolator = DecelerateInterpolator()
        }

        va.addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        va.addListener(object : AnimatorListenerWrapper() {
            override fun onAnimationEnd(animation: Animator?) {
                if (expand) {
                    layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    layoutParams.height = minHeight
                }
            }
        })
        va.duration = 400
        va.start()
    }

    fun getHeaderHeight() = getChildAt(0).measuredHeight

    private fun dpToPx(value: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.resources.displayMetrics).toInt()
}