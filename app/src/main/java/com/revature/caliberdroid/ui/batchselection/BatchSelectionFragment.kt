package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.SearchView
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

        setHasOptionsMenu(true)

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_bar, menu)

        (menu.findItem(R.id.search_bar).actionView as SearchView).apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    val filteredModelList: List<Batch> = filter(viewModel.batches.value!!, query)
                    (binding.recyclerviewBatchSelectionDisplayBatches.adapter as BatchSelectionAdapter).edit()
                        .replaceAll(filteredModelList)
                        .commit()
                    binding.recyclerviewBatchSelectionDisplayBatches.scrollToPosition(0)
                    return true
                }
            })

            queryHint = "Search by trainer's name"

            binding.root.setOnClickListener { this.clearFocus() }
        }
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

    private fun filter(
        models: List<Batch>,
        query: String
    ): List<Batch> {
        val lowerCaseQuery = query.toLowerCase(Locale.ROOT)
        val filteredModelList: MutableList<Batch> = ArrayList<Batch>()
        var text: String
        for (model in models) {
            text = model.trainerName!!.toLowerCase(Locale.ROOT)
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
    }
}
