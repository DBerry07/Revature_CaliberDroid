package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.databinding.ItemQualityAuditTraineeBinding
import com.revature.caliberdroid.ui.qualityaudit.trainees.QualityAuditTraineesFragment.TraineeStatusHandler
import com.revature.caliberdroid.util.KeyboardUtil


class QualityAuditTraineesAdapter(context: Context,
                                  comparator: Comparator<TraineeWithNotesLiveData>,
                                  val viewModel: QualityAuditTraineesViewModel
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
        return TraineeWithNotesViewHolder(
            ItemQualityAuditTraineeBinding.inflate(
                inflater,
                parent,
                false
            ), viewModel
        )
    }

    class TraineeWithNotesViewHolder(
        val binding: ItemQualityAuditTraineeBinding,
        val viewModel: QualityAuditTraineesViewModel
    ) : SortedListAdapter.ViewHolder<TraineeWithNotesLiveData>(binding.root) {


        override fun performBind(item: TraineeWithNotesLiveData) {
            binding.traineeWithNotes = item
            binding.includeItemaudittraineeStatusChooserLayout.root.visibility = View.GONE
            watchOverallNote()
            val statusHandler = TraineeStatusHandler(
                context = binding.root.context,
                binding = binding,
                viewModel = viewModel
            )
            binding.statusHandler = statusHandler
            binding.includeItemaudittraineeStatusChooserLayout.statusHandler = statusHandler
        }

        private fun watchOverallNote() {
            binding.etItemaudittraineeNotes.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus) {
                    KeyboardUtil.hideSoftKeyboard(binding.root.context, v)
                    viewModel.putTraineeNotes(binding.traineeWithNotes!!.value!!)
                }
            }

            binding.etItemaudittraineeNotes.addTextChangedListener {
                if (binding.traineeWithNotes!!.value!!.auditTraineeNotes!!.content != it.toString()) {
                    binding.traineeWithNotes!!.value!!.auditTraineeNotes!!.content = it.toString()
                    viewModel.startDelayedSaveThread(
                        binding.traineeWithNotes!!.value!!,
                        viewModel::putTraineeNotes
                    )
                }
            }
        }
    }

}