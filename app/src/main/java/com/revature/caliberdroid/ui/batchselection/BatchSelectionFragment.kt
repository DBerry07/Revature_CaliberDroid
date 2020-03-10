package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchSelectionBinding
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter.OnItemClickListener
import java.util.*

class BatchSelectionFragment : Fragment() {

    private var _binding: FragmentBatchSelectionBinding? = null
    private val binding
        get() = _binding!!
    private val batchSelectionViewModel: BatchSelectionViewModel by activityViewModels()
    private var arrayAdapter: ArrayAdapter<Int>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchSelectionBinding.inflate(inflater)

        batchSelectionViewModel.getData()

        initializeSpinner()
        initializeRecyclerView()

        subscribeToBatchesViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        arrayAdapter = null
        _binding = null
    }

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_BATCHES: Comparator<Batch> =
            Comparator<Batch> { a: Batch, b: Batch -> a.trainerName!!.compareTo(b.trainerName!!) }
    }

    private fun subscribeToBatchesViewModel() {
        batchSelectionViewModel.batches.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewBatchSelectionDisplayBatches.adapter as BatchSelectionAdapter).edit()
                .replaceAll(it)
                .commit()
        })

        batchSelectionViewModel.validYears.observe(viewLifecycleOwner, Observer {
            arrayAdapter?.apply {
                notifyDataSetChanged()
                binding.spinnerBatchSelectionSelectYear.apply {
                    selectedIndex = count
                }
            }
        })
    }

    private fun initializeSpinner() {
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            batchSelectionViewModel.validYears.value!!
        )

        binding.spinnerBatchSelectionSelectYear.setAdapter(arrayAdapter)
        binding.spinnerBatchSelectionSelectYear.setOnItemSelectedListener { view, position, id, item ->
            Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun initializeRecyclerView() {
        binding.recyclerviewBatchSelectionDisplayBatches.layoutManager =
            LinearLayoutManager(context)
        binding.recyclerviewBatchSelectionDisplayBatches.adapter = BatchSelectionAdapter(
            requireContext(),
            ALPHABETICAL_COMPARATOR_BATCHES,
            parentFragment as OnItemClickListener
        )
    }
}
