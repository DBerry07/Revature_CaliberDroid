package com.revature.caliberdroid.ui.batches

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchesBinding
import com.revature.caliberdroid.ui.batches.BatchAdapter.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_batches.view.*
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getDateTimeInstance
import java.time.LocalDateTime
import java.util.*

class ManageBatchFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentBatchesBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchesViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchesBinding.inflate(layoutInflater)

        binding.recyclerviewManageBatches.layoutManager = LinearLayoutManager(activity)
        binding.recyclerviewManageBatches.adapter = BatchAdapter(requireContext(), ALPHABETICAL_COMPARATOR_BATCHES, this)
        viewModel.getBatches()
        subscribeToViewModel()
        binding.tvManageBatchesNoOfBatchesValue.text = binding.root.recyclerview_manage_batches.adapter?.itemCount.toString()
        binding.btnManageBatchCreateBatch.setOnClickListener {
            findNavController().navigate(ManageBatchFragmentDirections.actionManageBatchFragmentToCreateBatchFragment(null))
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.years_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding.spinnerManageBatches.adapter = adapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_BATCHES: Comparator<Batch> =
            Comparator<Batch> { a: Batch, b: Batch -> a.trainerName!!.compareTo(b.trainerName!!) }
    }

    override fun onBatchClick(batchClicked: Batch, path: Int) {
        if(path == 1)
            findNavController().navigate(ManageBatchFragmentDirections.actionManageBatchFragmentToCreateBatchFragment(batchClicked))
        if(path == 2)
            findNavController().navigate(ManageBatchFragmentDirections.actionManageBatchFragmentToTraineeFragment(batchClicked))
    }

    private fun subscribeToViewModel() {
        viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewManageBatches.adapter as BatchAdapter).edit()
                .replaceAll(it)
                .commit()
        })
    }

}




