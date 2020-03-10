package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchSelectionBinding
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter.OnItemClickListener
import java.util.*

class BatchSelectionFragment : Fragment(), OnItemSelectedListener {

    private var _binding: FragmentBatchSelectionBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchSelectionViewModel by activityViewModels()

    var yearsArrayAdapter: ArrayAdapter<Int>? = null
    var quartersArrayAdapter: ArrayAdapter<String>? = null

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
                if (it.isNotEmpty()) {
                    binding.spinnerBatchSelectionSelectYear.apply {
                        if (viewModel.selectedYear == null) {
                            viewModel.selectedYear = it.max()
                        }

                        this.setSelection(it.indexOf(viewModel.selectedYear))
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

        binding.spinnerBatchSelectionSelectYear.adapter = yearsArrayAdapter!!
        binding.spinnerBatchSelectionSelectYear.onItemSelectedListener = this
        if (viewModel.selectedYear != null) {
            binding.spinnerBatchSelectionSelectYear.setSelection(
                yearsArrayAdapter!!.getPosition(
                    viewModel.selectedYear!!
                )
            )
        }

        binding.spinnerBatchSelectionSelectQuarter.adapter = quartersArrayAdapter!!
        binding.spinnerBatchSelectionSelectQuarter.onItemSelectedListener = this
        binding.spinnerBatchSelectionSelectQuarter.setSelection(
            quartersArrayAdapter!!.getPosition(
                viewModel._selectedQuarter
            )
        )
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.spinner_batch_selection_select_year -> viewModel.selectedYear =
                parent.getItemAtPosition(position) as Int
            else -> viewModel._selectedQuarter = parent?.getItemAtPosition(position) as String
        }
    }
}
