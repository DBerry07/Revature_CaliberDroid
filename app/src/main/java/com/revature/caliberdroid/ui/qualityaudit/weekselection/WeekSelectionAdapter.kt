package com.revature.caliberdroid.ui.qualityaudit.weekselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.databinding.ItemQualityAuditWeekBinding

class WeekSelectionAdapter(context: Context,
                           comparator: Comparator<WeekLiveData>,
                           private val onItemClickListener: OnItemClickListener
) : SortedListAdapter<WeekLiveData>(context, WeekLiveData::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): WeekViewHolder {
        val binding = ItemQualityAuditWeekBinding.inflate(inflater, parent, false)

        return WeekViewHolder(binding, onItemClickListener)
    }

    class WeekViewHolder(
        val binding: ItemQualityAuditWeekBinding,
        val onItemClickListener: OnItemClickListener
    ) :
        SortedListAdapter.ViewHolder<WeekLiveData>(binding.root), View.OnClickListener {
//
//        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: WeekLiveData) {
            binding.weekLiveData = item
        }

        override fun onClick(v: View?) {
            onItemClickListener.onWeekClick(currentItem)
        }
//
//        override fun getLifecycle(): Lifecycle {
//            return lifecycleRegistry
//        }
//
    }

    interface OnItemClickListener {
        fun onWeekClick(weekClicked: WeekLiveData)
    }

}