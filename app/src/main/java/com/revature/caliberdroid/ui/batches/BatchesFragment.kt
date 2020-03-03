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

    private var _binding: BatchesFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: BatchesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = BatchesFragmentBinding.inflate(layoutInflater)

        viewModel.getBatches()

        binding.apply {

            // New way of inserting the data into the ui directly from the view model, all changes are automatically updated on the UI
            batchesViewModel = viewModel

            // Make sure to call this method so the UI reflect the changes on the view model
            setLifecycleOwner(this@BatchesFragment)

            viewModel.batchesLiveData.observe(viewLifecycleOwner, Observer {

            })

            btnNextPage.setOnClickListener {
                findNavController().navigate(
                    BatchesFragmentDirections.actionBatchesFragmentToGalleryFragment(
                        Batch(1)
                    )
                )
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
