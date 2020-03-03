package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentBatchSelectionBinding
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter.OnItemClickListener
import androidx.lifecycle.Observer
import java.util.*

class BatchSelectionFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentBatchSelectionBinding? = null
    private val binding
        get() = _binding!!
    private val batchSelectionViewModel: BatchSelectionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBatchSelectionBinding.inflate(inflater)

        binding.recyclerviewBatchSelectionDisplayBatches.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewBatchSelectionDisplayBatches.adapter = BatchSelectionAdapter(requireContext(), ALPHABETICAL_COMPARATOR_BATCHES, this)

        batchSelectionViewModel.getBatches()

        subscribeToBatchesViewModel()

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

    override fun onBatchClick(batchClicked: Batch) {
        TODO("Not yet implemented")
    }

    private fun subscribeToBatchesViewModel() {
        batchSelectionViewModel.batches.observe(viewLifecycleOwner, Observer {
            (binding.recyclerviewBatchSelectionDisplayBatches.adapter as BatchSelectionAdapter).edit()
                .replaceAll(it)
                .commit()
        })
    }
}
