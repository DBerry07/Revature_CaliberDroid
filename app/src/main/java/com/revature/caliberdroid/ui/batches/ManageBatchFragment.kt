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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchesBinding
import com.revature.caliberdroid.ui.batches.BatchAdapter.OnItemClickListener
import java.util.*


class ManageBatchFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentBatchesBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchesViewModel by activityViewModels()

    private lateinit var cancelBtn: Button
    private lateinit var createBtn: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchesBinding.inflate(layoutInflater)

        binding.recyclerviewManageBatches.layoutManager = LinearLayoutManager(activity)
        binding.recyclerviewManageBatches.adapter = BatchAdapter(requireContext(), ALPHABETICAL_COMPARATOR_BATCHES, this)

        viewModel.getBatches()
        subscribeToViewModel()

        binding.btnManageBatchCreateBatch.setOnClickListener {
            createBatchDialog()
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
            Toast.makeText(context,"ok", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_BATCHES: Comparator<Batch> =
            Comparator<Batch> { a: Batch, b: Batch -> a.trainerName!!.compareTo(b.trainerName!!) }
    }

    override fun onBatchClick(batchClicked: Batch) {
        findNavController().navigate(ManageBatchFragmentDirections.actionManageBatchFragmentToBatchDetailsFragment(batchClicked))
    }

    private fun subscribeToViewModel() {
        viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewManageBatches.adapter as BatchAdapter).edit()
                .replaceAll(it)
                .commit()
        })
    }
}



