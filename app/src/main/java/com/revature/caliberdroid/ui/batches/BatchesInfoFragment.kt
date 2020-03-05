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
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R

class BatchesInfo : Fragment() {

    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var viewBtn: Button

    private lateinit var cancelBtn: Button
    private lateinit var createBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_batches_info, container, false)

        editBtn = root.findViewById(R.id.btn_batch_info_edit)
        deleteBtn = root.findViewById(R.id.btn_batch_info_delete)
        viewBtn = root.findViewById(R.id.btn_batch_info_view)

        deleteBtn.setOnClickListener { Snackbar.make(view!!,"DELETE BUTTON",Snackbar.LENGTH_SHORT).show() }
        viewBtn.setOnClickListener {
            findNavController().navigate(BatchesInfoDirections.actionBatchDetailsFragmentToTraineeFragment())
        }

        editBtn.setOnClickListener {
            createBatchDialog(root)
        }

        return root
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

        trainerName.text = root.findViewById<TextView>(R.id.tv_batch_info_name_trainer_value).text
        batchName.text = root.findViewById<TextView>(R.id.tv_batch_info_name).text
        locationName.text = root.findViewById<TextView>(R.id.tv_batch_info_location_value).text
        skillFocus.text = root.findViewById<TextView>(R.id.tv_batch_info_skill_value).text
        startDate.text = root.findViewById<TextView>(R.id.tv_batch_info_start_value).text
        endDate.text = root.findViewById<TextView>(R.id.tv_batch_info_end_value).text
        goodGrade.text = root.findViewById<TextView>(R.id.tv_batch_info_good_grade_value).text
        passingGrade.text = root.findViewById<TextView>(R.id.tv_create_batch_passing_grade_value).text
    }
}
