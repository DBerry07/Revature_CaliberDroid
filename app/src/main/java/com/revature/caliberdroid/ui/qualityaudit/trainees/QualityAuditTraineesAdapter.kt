package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.R
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

            binding.imgItemaudittraineeFlag.setOnClickListener {
                showEditFlagDialog(item, viewModel)
            }

            //display red or green flag, or no flag
            if(item.value!!.trainee!!.flagStatus.equals("RED")){
                binding.imgItemaudittraineeFlag.setImageResource(R.drawable.ic_red_flag)
            } else if(item.value!!.trainee!!.flagStatus.equals("GREEN")){
                binding.imgItemaudittraineeFlag.setImageResource(R.drawable.ic_green_flag)
            } else {
                binding.imgItemaudittraineeFlag.visibility = View.GONE
            }

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

        private fun showEditFlagDialog(
            item: TraineeWithNotesLiveData,
            viewModel: QualityAuditTraineesViewModel
        ) {

            val alertDialog = AlertDialog.Builder(binding.root.context)
            alertDialog.setTitle("Comment for ${item.value!!.trainee!!.name}")

            val editText = EditText(binding.root.context)

            editText.setText(
                if (item.value!!.trainee!!._flagNotes != "null") {
                    item.value!!.trainee!!._flagNotes
                } else {
                    ""
                }
            )

            alertDialog.setView(editText)

            alertDialog.setPositiveButton("Update comment") { _, _ ->
                Toast.makeText(binding.root.context, "Update clicked", Toast.LENGTH_SHORT).show()
                item.value!!.trainee!!.flagNotes = editText.text.toString()
                viewModel.putTrainee(trainee = item.value!!.trainee!!)
            }

            alertDialog.setNeutralButton("Cancel") { _, _ ->
                Toast.makeText(binding.root.context, "Neutral clicked", Toast.LENGTH_SHORT).show()
            }

            alertDialog.setNegativeButton("Delete comment") { dialog, _ ->
                Toast.makeText(binding.root.context, "Delete comment clicked", Toast.LENGTH_SHORT)
                    .show()
                item.value!!.trainee!!.flagNotes = ""
                viewModel.putTrainee(item.value!!.trainee!!)
            }



            alertDialog.show()
        }

    }

}