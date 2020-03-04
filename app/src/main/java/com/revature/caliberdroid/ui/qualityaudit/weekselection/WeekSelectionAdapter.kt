package com.revature.caliberdroid.ui.qualityaudit.weekselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditWeekBinding

class WeekSelectionAdapter(context: Context, comparator: Comparator<AuditWeekNotes>, private val onItemClickListener: OnItemClickListener)
    : SortedListAdapter<AuditWeekNotes>(context, AuditWeekNotes::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): WeekViewHolder {
        return WeekViewHolder(ItemQualityAuditWeekBinding.inflate(inflater,parent,false), onItemClickListener)
    }

    class WeekViewHolder(val binding: ItemQualityAuditWeekBinding, val onItemClickListener: OnItemClickListener)
        : SortedListAdapter.ViewHolder<AuditWeekNotes>(binding.getRoot()), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: AuditWeekNotes) {

        }

        override fun onClick(v: View?) {
            onItemClickListener.onWeekClick(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onWeekClick(weekClicked: AuditWeekNotes)
    }

}