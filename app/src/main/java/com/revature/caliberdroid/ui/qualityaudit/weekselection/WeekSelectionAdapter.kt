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
import com.revature.caliberdroid.BR
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditWeekBinding
import com.revature.caliberdroid.util.AuditStatusConverter

class WeekSelectionAdapter(context: Context,
                           comparator: Comparator<AuditWeekNotes>,
                           private val onItemClickListener: OnItemClickListener
) : SortedListAdapter<AuditWeekNotes>(context, AuditWeekNotes::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): WeekViewHolder {
        return WeekViewHolder(ItemQualityAuditWeekBinding.inflate(inflater,parent,false), onItemClickListener)
    }

    class WeekViewHolder(val binding: ItemQualityAuditWeekBinding, val onItemClickListener: OnItemClickListener)
        : SortedListAdapter.ViewHolder<AuditWeekNotes>(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: AuditWeekNotes) {
            binding.auditWeekNotes = item
            if (item.overallStatus.get() == null) {
                item.overallStatus.set("Undefined")
            }
            binding.imgItemauditweekOverallstatus.setImageResource(AuditStatusConverter.getImageResourceID(item.overallStatus.get()!!))
        }

        override fun onClick(v: View?) {
            onItemClickListener.onWeekClick(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onWeekClick(weekClicked: AuditWeekNotes)
    }

}