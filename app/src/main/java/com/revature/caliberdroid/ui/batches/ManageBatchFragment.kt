package com.revature.caliberdroid.ui.batches

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.batches.BatchesAdapter
import com.revature.caliberdroid.data.api.APIHandler.context
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.ui.batches.BatchesViewModel
import com.revature.caliberdroid.databinding.FragmentBatchesBinding
import java.util.*
import kotlin.collections.ArrayList


class ManageBatchFragment : Fragment() {

    private var temp = ArrayList(Arrays.asList("", "", "", "", "", ""))

    private var _binding: FragmentBatchesBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchesViewModel by activityViewModels()

    private lateinit var cancelBtn: Button
    private lateinit var createBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchesBinding.inflate(layoutInflater)
        viewModel.getBatches()
        binding.apply {
            // New way of inserting the data into the ui directly from the view model, all changes are automatically updated on the UI
            // batchesViewModel = viewModel

            // Make sure to call this method so the UI reflect the changes on the view model
            // setLifecycleOwner(this@BatchesFragment)
            viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer { batches->
                recyclerviewManageBatches.adapter = context?.let { BatchesAdapter(it,
                    batches as ArrayList<Batch>
                ) }
            })

            btnManageBatchCreateBatch.setOnClickListener {
                createBatchDialog()
            }

        }
        val mRecyclerView = binding.root.findViewById<RecyclerView>(R.id.recyclerview_manage_batches)
        mRecyclerView.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
        }
        return binding.root
    }

    private fun createBatchDialog() {
        val dialog = activity?.let { Dialog(it) }
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        val root = R.layout.create_batch_dialog
        dialog?.setContentView(root)
        dialog?.show()

        cancelBtn = dialog!!.findViewById(R.id.btn_create_batch_cancel)
        createBtn = dialog.findViewById(R.id.btn_create_batch_create)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
        createBtn.setOnClickListener {
            dialog.dismiss()
            findNavController().navigate(ManageBatchFragmentDirections.actionManageBatchFragmentToBatchDetailsFragment())
            Toast.makeText(context,"ok", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun onClick() {
            Toast.makeText(context,"Batch clicked", Toast.LENGTH_SHORT).show()
        }
    }
}


