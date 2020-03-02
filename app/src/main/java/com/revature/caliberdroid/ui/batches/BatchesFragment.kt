package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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

        // New way of inserting the data into the ui directly from the view model, all changes are automatically updated on the UI
        binding.batchesViewModel = viewModel

        // Make sure to call this method so the UI reflect the changes on the view model
        binding.setLifecycleOwner(this)

//        subscribeToBatches()

//        findNavController().navigate(BatchesFragmentDirections.actionBatchesFragmentToGalleryFragment(
//            Batch(1)
//        ))

        return binding.root
    }

    //    fun subscribeToBatches() {
//        viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {
//            binding
//        })
//    }

}
