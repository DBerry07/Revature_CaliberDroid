package com.revature.caliberdroid.ui.batches

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentBatchesBinding
import com.revature.caliberdroid.databinding.FragmentBatchesInfoBinding

class BatchesInfo : Fragment() {

    private var _binding: FragmentBatchesInfoBinding? = null
    private val binding
        get() = _binding!!
    private val args: BatchesInfoArgs by navArgs()

    private lateinit var cancelBtn: Button
    private lateinit var createBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val root = inflater.inflate(R.layout.fragment_batches_info, container, false)
        _binding = FragmentBatchesInfoBinding.inflate(layoutInflater)

        binding.btnBatchInfoDelete.setOnClickListener { Snackbar.make(view!!,"DELETE BUTTON",Snackbar.LENGTH_SHORT).show() }
        binding.btnBatchInfoView.setOnClickListener {
            findNavController().navigate(BatchesInfoDirections.actionBatchDetailsFragmentToTraineeFragment())
        }
        binding.btnBatchInfoEdit.setOnClickListener {
            createBatchDialog(binding.root)
        }
        binding.batchModel = args.selectedBatch

        return binding.root
    }



    private fun createBatchDialog(root: View) {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.create_batch_dialog)
        dialog?.show()

        val header: TextView = dialog!!.findViewById(R.id.tv_create_batch_header)
        setDialogValues(dialog, root)
        cancelBtn = dialog.findViewById(R.id.btn_create_batch_cancel)
        createBtn = dialog.findViewById(R.id.btn_create_batch_create)

        createBtn.text = getString(R.string.batchesInfo_confirmButton)
        header.text = getString(R.string.batchesInfo_cancelButton)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        createBtn.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context,"Batch Updated!", Toast.LENGTH_SHORT).show()
        }

    }


    private fun setDialogValues(dialog: Dialog, root: View) {
        val trainerName: TextView = dialog.findViewById(R.id.et_create_batch_name_trainer_input)
        val batchName: TextView = dialog.findViewById(R.id.et_create_batch_name_input)
        val locationName: TextView = dialog.findViewById(R.id.et_create_batch_location_input)
        val skillFocus: TextView = dialog.findViewById(R.id.et_create_batch_skill_input)
        val startDate: TextView = dialog.findViewById(R.id.et_create_batch_start_input)
        val endDate: TextView = dialog.findViewById(R.id.et_create_batch_end_input)
        val goodGrade: TextView = dialog.findViewById(R.id.et_create_batch_good_grade_input)
        val passingGrade: TextView = dialog.findViewById(R.id.et_create_batch_passing_grade_input)

        trainerName.text = binding.tvBatchInfoNameTrainerValue.text
        batchName.text = binding.tvBatchInfoName.text
        locationName.text = binding.tvBatchInfoLocationValue.text
        skillFocus.text = binding.tvBatchInfoSkillValue.text
        startDate.text = binding.tvBatchInfoStartValue.text
        endDate.text = binding.tvBatchInfoEndValue.text
        goodGrade.text = binding.tvBatchInfoGoodGradeValue.text
        passingGrade.text = binding.tvCreateBatchPassingGradeValue.text
    }
}
