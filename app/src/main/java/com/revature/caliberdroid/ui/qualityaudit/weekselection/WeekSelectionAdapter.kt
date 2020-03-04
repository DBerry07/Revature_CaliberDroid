package com.revature.caliberdroid.ui.qualityaudit.weekselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditWeekBinding

class WeekSelectionAdapter(context: Context,
                           comparator: Comparator<WeekLiveData>,
                           private val onItemClickListener: OnItemClickListener,
                           val viewLifecycleOwner: LifecycleOwner
) : SortedListAdapter<WeekLiveData>(context, WeekLiveData::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): WeekViewHolder {
        return WeekViewHolder(ItemQualityAuditWeekBinding.inflate(inflater,parent,false), onItemClickListener, viewLifecycleOwner)
    }

    class WeekViewHolder(val binding: ItemQualityAuditWeekBinding, val onItemClickListener: OnItemClickListener, val viewLifecycleOwner: LifecycleOwner)
        : SortedListAdapter.ViewHolder<WeekLiveData>(binding.getRoot()), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
            binding.setLifecycleOwner(viewLifecycleOwner)
        }

        override fun performBind(item: WeekLiveData) {
            binding.weekLiveData = item
            if (item.value!!.overallNotes.equals("null")) {
                binding.tvAuditweekOverallNotes.text = ""
            } else {
                binding.tvAuditweekOverallNotes.text = item.value!!.overallNotes
            }
        }

        override fun onClick(v: View?) {
            onItemClickListener.onWeekClick(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onWeekClick(weekClicked: WeekLiveData)
    }

}