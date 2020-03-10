package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.BR
import com.revature.caliberdroid.data.model.AuditTraineeNotes
import com.revature.caliberdroid.databinding.ItemQualityAuditTraineeBinding
import timber.log.Timber


class QualityAuditTraineesAdapter(context: Context,
                                  comparator: Comparator<TraineeWithNotesLiveData>
) : SortedListAdapter<TraineeWithNotesLiveData>(
    context,
    TraineeWithNotesLiveData::class.java,
    comparator
) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): TraineeWithNotesViewHolder {
        return TraineeWithNotesViewHolder(ItemQualityAuditTraineeBinding.inflate(inflater,parent,false))
    }

    class TraineeWithNotesViewHolder(
        val binding: ItemQualityAuditTraineeBinding
    ) : SortedListAdapter.ViewHolder<TraineeWithNotesLiveData>(binding.root) {


        override fun performBind(item: TraineeWithNotesLiveData) {
            binding.traineeWithNotes = item
            item.value!!.addOnPropertyChangedCallback(object : OnPropertyChangedCallback() {
                override fun onPropertyChanged(
                    sender: Observable,
                    propertyId: Int
                ) {
                    Timber.d("Sender: $sender. Property ID: $propertyId")

                    when (propertyId) {
                        BR.content -> Snackbar.make(
                            binding.root,
                            "New content -> ${(sender as AuditTraineeNotes).content}",
                            Snackbar.LENGTH_LONG
                        ).show()
                        else -> Timber.d("OnElse")
                    }
                }
            })
        }

    }

}