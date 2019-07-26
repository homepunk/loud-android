package homepunk.github.com.presentation.feature.widget.timeline

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import homepunk.github.com.presentation.BR
import homepunk.github.com.presentation.R
import homepunk.github.com.presentation.common.adapter.SimpleBindingRecyclerAdapter
import homepunk.github.com.presentation.common.adapter.StickHeaderItemDecoration
import homepunk.github.com.presentation.common.adapter.StickyAdapter
import homepunk.github.com.presentation.core.ext.addAllToEmptyList
import homepunk.github.com.presentation.core.ext.dayOfMonth
import homepunk.github.com.presentation.core.ext.month
import homepunk.github.com.presentation.core.listener.OnItemClickListener
import homepunk.github.com.presentation.core.listener.OnNotifyDataSeChangedListener
import homepunk.github.com.presentation.core.wrapper.AnimationListenerWrapper
import homepunk.github.com.presentation.databinding.CustomLayoutTimelineViewBinding
import homepunk.github.com.presentation.feature.widget.timeline.model.TimelineEventModel
import homepunk.github.com.presentation.feature.widget.timeline.model.TimelineMonthModel
import timber.log.Timber


/**Created by Homepunk on 09.05.2019. **/
class TimelineView<T> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RelativeLayout(context, attrs, defStyleAttr) {

    private var stickyDateHeaderDecoration: StickHeaderItemDecoration? = null
    private var resourceViewBinding: CustomLayoutTimelineViewBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.custom_layout_timeline_view, this, true)

    private var mRvMonthsAdapter: SimpleBindingRecyclerAdapter<TimelineMonthModel>? = null
    private var mRvTimelineAdapter: TimelineStickyDateRecyclerAdapter<T>? = null

    private var mMonthList = ObservableArrayList<TimelineMonthModel>()
    private var mTimelineList = ObservableArrayList<TimelineEventModel<T>>()

    var adapter: TimelineAdapter<T>? = null
        set(value) {
            field = value?.apply {
                onNotifyDataSeChangedListener = object : OnNotifyDataSeChangedListener {
                    override fun onDataSetChanged() {
                        setUpData()
                    }
                }
            }
            setUpLayout()
        }

    var currentMonthIndex: Int = 0
    var selectedMonthIndex: Int = -1

    private fun setUpLayout() {
        with(resourceViewBinding) {
            mRvMonthsAdapter = SimpleBindingRecyclerAdapter(R.layout.custom_layout_item_timeline_month, BR.model, mMonthList)
            mRvMonthsAdapter!!.onItemClickListener = object : OnItemClickListener<TimelineMonthModel> {
                override fun onClick(position: Int, item: TimelineMonthModel) {
                    var indexOfFirstMonthItem = 0/*mTimelineList.indexOfFirst { it.monthIndex == position }*/
                    var headerCount = 0
                    mTimelineList.forEachIndexed { index, item ->
                        if (item.itemType == TimelineEventModel.HEADER) {
                            headerCount++
                        }
                        if (item.monthIndex == position) {
                            indexOfFirstMonthItem = position
                            return
                        }
                    }
                    Timber.w("TIMELINE indexOfFirstMonthItem $indexOfFirstMonthItem, headerCount = $headerCount ${mTimelineList[indexOfFirstMonthItem].getDay()}")
                    if (currentMonthIndex != position) {
                        rvTimeline.smoothScrollToPosition(indexOfFirstMonthItem)
                    }
                }
            }
            mRvTimelineAdapter = TimelineStickyDateRecyclerAdapter(adapter!!.getDefaultLayoutId(), adapter!!.getDateLayoutId(), mTimelineList)
                    .apply {
                        onNewDateListener = object : TimelineStickyDateRecyclerAdapter.OnNewDateListener<TimelineEventModel<T>> {
                            override fun onNewDate(item: TimelineEventModel<T>) {
                                Timber.w("TIMELINE On new date currentMonthIndex $currentMonthIndex, monthIndex ${item.monthIndex}")
                                if (currentMonthIndex != item.monthIndex) {
                                    if (currentMonthIndex >= 0) {
                                        mMonthList[currentMonthIndex].isCurrent.set(false)
                                    }
                                    currentMonthIndex = item.monthIndex
                                    mMonthList[currentMonthIndex].isCurrent.set(true)
                                }
                            }
                        }
                    }
            rvMonths.adapter = mRvMonthsAdapter
            rvMonths.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_from_right)

            rvTimeline.adapter = mRvTimelineAdapter
            rvTimeline.layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_from_bottom)
            rvTimeline.layoutAnimationListener = object : AnimationListenerWrapper() {
                override fun onAnimationEnd(animation: Animation?) {
                    stickyDateHeaderDecoration = StickHeaderItemDecoration(mRvTimelineAdapter!! as StickyAdapter<RecyclerView.ViewHolder, RecyclerView.ViewHolder>)
                    stickyDateHeaderDecoration!!.attachToRecyclerView(resourceViewBinding.rvTimeline)
                    Timber.w("onAnimationEnd itemDecorationCount = ${rvTimeline.itemDecorationCount}")
                }

                override fun onAnimationStart(animation: Animation?) {
                    stickyDateHeaderDecoration?.attachToRecyclerView(null)
                    stickyDateHeaderDecoration = null
                    Timber.w("onAnimationStart itemDecorationCount = ${rvTimeline.itemDecorationCount}")
                }
            }
        }
    }

    private fun setUpData() {
        this.currentMonthIndex = -1
        var currentDay = -1
        var currentDayIndex = 0

        var currentMonthNum = -1
        var currentMonthIndex = -1

        val monthList = arrayListOf<TimelineMonthModel>()
        val itemList = arrayListOf<TimelineEventModel<T>>()

        adapter!!.itemList!!.forEach { item ->
            val date = adapter!!.getDate(item)
            val day = date.dayOfMonth()
            val monthNum = date.month()

            if (monthNum != -1 && monthNum != currentMonthNum) {
                currentMonthNum = monthNum
                monthList.add(TimelineMonthModel(adapter!!.getMonthText(item)))
                currentMonthIndex++
            }

            if (currentDay != Integer.valueOf(day)) {
                currentDay = Integer.valueOf(day)
                currentDayIndex = itemList.size
                itemList.add(TimelineEventModel(item, currentDayIndex, currentMonthIndex, TimelineEventModel.HEADER, adapter!!::getDateText))
            }
            itemList.add(TimelineEventModel(item, currentDayIndex, currentMonthIndex, TimelineEventModel.ITEM, adapter!!::getDateText))
        }
        mMonthList.addAllToEmptyList(monthList)
        resourceViewBinding.rvMonths.scheduleLayoutAnimation()
        mTimelineList.addAllToEmptyList(itemList)
        resourceViewBinding.rvTimeline.scheduleLayoutAnimation()

    }
}
