package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.BatchesFragmentBinding

class BatchesFragment : Fragment() {

    private lateinit var binding: BatchesFragmentBinding
    private val viewModel: BatchesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BatchesFragmentBinding.inflate(layoutInflater)

        viewModel.getBatches()

        subscribeToBatches()

        findNavController().navigate(BatchesFragmentDirections.actionBatchesFragmentToGalleryFragment(
            Batch(1)
        ))

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

    fun subscribeToBatches() {
        viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {
            binding.batch = it
        })
    }

}
