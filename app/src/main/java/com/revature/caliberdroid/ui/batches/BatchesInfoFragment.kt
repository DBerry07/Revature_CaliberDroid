package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R

class BatchesInfo : Fragment() {

    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var viewBtn: Button

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

        editBtn.setOnClickListener { Snackbar.make(view!!,"EDIT BUTTON",Snackbar.LENGTH_SHORT).show() }
        deleteBtn.setOnClickListener { Snackbar.make(view!!,"DELETE BUTTON",Snackbar.LENGTH_SHORT).show() }
        viewBtn.setOnClickListener { Snackbar.make(view!!,"VIEW ASSOCIATES",Snackbar.LENGTH_SHORT).show() }


        return root
    }


}
