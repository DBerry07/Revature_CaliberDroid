package com.revature.caliberdroid.ui.qualityaudit.trainees

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
import com.revature.caliberdroid.data.model.TraineeWithNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditTraineeBinding
import com.revature.caliberdroid.databinding.ItemQualityAuditWeekBinding

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
        : SortedListAdapter.ViewHolder<TraineeWithNotes>(binding.getRoot()) {


        override fun performBind(item: TraineeWithNotes) {
            binding.traineeWithNotes = item
        }

    }

}