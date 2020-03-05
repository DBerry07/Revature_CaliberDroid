package com.revature.caliberdroid.ui.assessbatch.weekselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.databinding.ItemAssessweekselectionWeekBinding

class WeekSelectionAdapter(
    val context: Context,
    val comparator: Comparator<AssessWeekNotes>,
    private val onItemClickListener: OnItemClickListener
    ) : SortedListAdapter<AssessWeekNotes>(context, AssessWeekNotes::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<out AssessWeekNotes> {
        return WeekViewHolder(ItemAssessweekselectionWeekBinding.inflate(inflater,parent,false), onItemClickListener)
    }

    class WeekViewHolder(val binding: ItemAssessweekselectionWeekBinding, val onItemClickListener: OnItemClickListener)
        : SortedListAdapter.ViewHolder<AssessWeekNotes>(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: AssessWeekNotes) {
            binding.assessWeekNotes = item
        }

        override fun onClick(v: View?) {
            onItemClickListener.onWeekClick(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onWeekClick(weekClicked: AssessWeekNotes)
    }

}