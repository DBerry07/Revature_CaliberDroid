package com.revature.caliberdroid.ui.batches

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
        viewBtn.setOnClickListener { Snackbar.make(view!!,"VIEW ASSOCIATES",Snackbar.LENGTH_SHORT).show() }

        editBtn.setOnClickListener {
            createBatchDialog()

        }

        return root
    }


    private fun createBatchDialog() {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        val root = R.layout.create_batch_dialog
        dialog?.setContentView(root)
        dialog?.show()

        var header: TextView = dialog!!.findViewById(R.id.tv_create_batch_header)
        cancelBtn = dialog!!.findViewById(R.id.btn_create_batch_cancel)
        createBtn = dialog!!.findViewById(R.id.btn_create_batch_create)

        createBtn.text = "Confirm"
        header.text = "Edit Batch"


        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        createBtn.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(context,"Batch Updated!", Toast.LENGTH_SHORT).show()
        }

    }
}
