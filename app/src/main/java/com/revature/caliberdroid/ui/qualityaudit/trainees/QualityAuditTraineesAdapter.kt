package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.TraineeWithNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditTraineeBinding

class QualityAuditTraineesAdapter(context: Context,
                                  comparator: Comparator<TraineeWithNotes>
) : SortedListAdapter<TraineeWithNotes>(context, TraineeWithNotes::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): TraineeWithNotesViewHolder {
        return TraineeWithNotesViewHolder(ItemQualityAuditTraineeBinding.inflate(inflater,parent,false))
    }

    class TraineeWithNotesViewHolder(val binding: ItemQualityAuditTraineeBinding)
        : SortedListAdapter.ViewHolder<TraineeWithNotes>(binding.root) {


        override fun performBind(item: TraineeWithNotes) {
            binding.traineeWithNotes = item
        }

    }

}