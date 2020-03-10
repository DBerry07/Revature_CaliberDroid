package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditTraineeWithNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditTraineeBinding

class QualityAuditTraineesAdapter(context: Context,
                                  comparator: Comparator<AuditTraineeWithNotes>
) : SortedListAdapter<AuditTraineeWithNotes>(
    context,
    AuditTraineeWithNotes::class.java,
    comparator
) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): TraineeWithNotesViewHolder {
        return TraineeWithNotesViewHolder(ItemQualityAuditTraineeBinding.inflate(inflater,parent,false))
    }

    class TraineeWithNotesViewHolder(val binding: ItemQualityAuditTraineeBinding) :
        SortedListAdapter.ViewHolder<AuditTraineeWithNotes>(binding.root) {


        override fun performBind(item: AuditTraineeWithNotes) {
            binding.traineeWithNotes = item
        }

    }

}