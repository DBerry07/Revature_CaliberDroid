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
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchSelectionBinding
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter.OnItemClickListener
import timber.log.Timber
import java.util.*

class BatchSelectionFragment : Fragment() {

    private var _binding: FragmentBatchSelectionBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchSelectionViewModel by activityViewModels()
    private var yearsArrayAdapter: ArrayAdapter<Int>? = null
    private var quartersArrayAdapter: ArrayAdapter<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchSelectionBinding.inflate(inflater)

        viewModel.getData()

        initializeSpinners()
        initializeRecyclerView()

        subscribeToBatchesViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        yearsArrayAdapter = null
        quartersArrayAdapter = null
        _binding = null
    }

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_BATCHES: Comparator<Batch> =
            Comparator<Batch> { a: Batch, b: Batch -> a.trainerName!!.compareTo(b.trainerName!!) }
    }

    private fun subscribeToBatchesViewModel() {
        viewModel.batches.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewBatchSelectionDisplayBatches.adapter as BatchSelectionAdapter).edit()
                .replaceAll(it)
                .commit()
        })

        viewModel.validYears.observe(viewLifecycleOwner, Observer {
            yearsArrayAdapter?.apply {
                notifyDataSetChanged()
                if (it.size > 0) {
                    binding.spinnerBatchSelectionSelectYear.apply {
                        viewModel.selectedYear = it.max()
                        selectedIndex = it.indexOf(viewModel.selectedYear)
                    }
                }
            }
        })
    }

    private fun initializeSpinners() {
        yearsArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.validYears.value!!
        )

        quartersArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.quarters
        )

        binding.spinnerBatchSelectionSelectYear.setAdapter(yearsArrayAdapter!!)
        binding.spinnerBatchSelectionSelectYear.setOnItemSelectedListener { view, position, id, item ->
            viewModel.selectedYear = item as Int
            Timber.d("Clicked $item at position: $position")
        }

        binding.spinnerBatchSelectionSelectQuarter.setAdapter(quartersArrayAdapter!!)
        binding.spinnerBatchSelectionSelectQuarter.setOnItemSelectedListener { view, position, id, item ->
            viewModel.selectedQuarter = (item as String)[1].toString().toInt()
            Timber.d("Clicked $item at position: $position")
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
