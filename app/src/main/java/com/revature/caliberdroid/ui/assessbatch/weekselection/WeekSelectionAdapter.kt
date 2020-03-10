package com.revature.caliberdroid.ui.assessbatch.weekselection

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.databinding.ItemAssessweekselectionWeekBinding
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData

class WeekSelectionAdapter(
    val context: Context,
    val comparator: Comparator<AssessWeekLiveData>,
    private val onItemClickListener: OnItemClickListener
    ) : SortedListAdapter<AssessWeekLiveData>(context, AssessWeekLiveData::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<out AssessWeekLiveData> {
        return WeekViewHolder(ItemAssessweekselectionWeekBinding.inflate(inflater,parent,false), onItemClickListener)
    }

    class WeekViewHolder(val binding: ItemAssessweekselectionWeekBinding, val onItemClickListener: OnItemClickListener)
        : SortedListAdapter.ViewHolder<AssessWeekLiveData>(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        override fun performBind(item: AssessWeekLiveData) {
            binding.assessWeekNotes = item.value
        }

        override fun onClick(v: View?) {
            onItemClickListener.onWeekClick(currentItem)
        }

    }

    interface OnItemClickListener {
        fun onWeekClick(weekClicked: AssessWeekLiveData)
    }

}