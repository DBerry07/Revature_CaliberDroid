package com.revature.caliberdroid.ui.managebatches

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchesBinding
import com.revature.caliberdroid.ui.managebatches.BatchAdapter.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_batches.view.*
import java.util.*
import kotlin.collections.ArrayList


class ManageBatchFragment : Fragment(), OnItemClickListener, AdapterView.OnItemSelectedListener,
    SearchView.OnQueryTextListener {

    private var _binding: FragmentBatchesBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchesViewModel by activityViewModels()
    private var yearsArrayAdapter: ArrayAdapter<Int>? = null
    private val batches: ArrayList<Batch>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentBatchesBinding.inflate(layoutInflater)
        viewModel.getValidYears()
        subscribeToViewModel()

        // set up search bar
        setHasOptionsMenu(true)

        binding.tvManageBatchesNoOfBatchesValue.text = "0"
        binding.btnManageBatchCreateBatch.setOnClickListener {
            findNavController().navigate(ManageBatchFragmentDirections.actionManageBatchFragmentToCreateBatchFragment(null))
        }

        spinnerInit()
        recyclerViewInit()

        return binding.root
    }



    override fun onCreateOptionsMenu(menu: Menu, inflator: MenuInflater) {
        inflator.inflate(R.menu.search_bar, menu)

        val searchView: SearchView = menu.findItem(R.id.search_bar).actionView as SearchView
        searchView.setOnQueryTextListener(this)

    }

    override fun onStart() {
        super.onStart()
        viewModel.getValidYears()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        yearsArrayAdapter = null
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

    override fun onDelete(batch: Batch){
        (viewModel.batchesLiveData.value as ArrayList).remove(batch)
        findNavController().navigate(R.id.manageBatchFragment)
    }

    private fun subscribeToViewModel() {
        viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {
            binding.tvManageBatchesNoOfBatchesValue.text = it.size.toString()
            (binding.recyclerviewManageBatches.adapter as BatchAdapter).edit()
                .replaceAll(it)
                .commit()
        })

        viewModel.validYears.observe(viewLifecycleOwner, Observer {
            yearsArrayAdapter?.apply {
                notifyDataSetChanged()
                if (it.isNotEmpty()) {
                    binding.spinnerManageBatches.apply {
                        if (viewModel.selectedYear == null) {
                            viewModel.selectedYear = it.max()
                        }
                        this.setSelection(it.indexOf(viewModel.selectedYear))
                    }
                }
            }
        })
    }

    private fun spinnerInit() {
        yearsArrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.validYears.value!!
        )
        binding.spinnerManageBatches.adapter = yearsArrayAdapter!!
        binding.spinnerManageBatches.onItemSelectedListener = this

        if (viewModel.selectedYear != null) {
            binding.spinnerManageBatches.setSelection(
                yearsArrayAdapter!!.getPosition(
                    viewModel.selectedYear!!
                )
            )
        }
    }

    private fun recyclerViewInit() {
        binding.recyclerviewManageBatches.layoutManager =
            LinearLayoutManager(context)
        binding.recyclerviewManageBatches.adapter = BatchAdapter(
            requireContext(),
            ALPHABETICAL_COMPARATOR_BATCHES,
            this
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.selectedYear = parent?.getItemAtPosition(position) as Int
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        // filter based on Batch Names and Skill Types
        viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewManageBatches.adapter as BatchAdapter).edit()
                .removeAll()
                .commit()
            var count = 0
            for(i in it){
                if(
                    i.trainingName.toLowerCase(Locale.ROOT).contains(newText.toString()) ||
                    i.skillType!!.toLowerCase(Locale.ROOT).contains(newText.toString()))
                {
                    (binding.recyclerviewManageBatches.adapter as BatchAdapter).edit().add(i).commit()
                    count++
                }
            }
            binding.tvManageBatchesNoOfBatchesValue.text = count.toString()
        })
        return false
    }

}




