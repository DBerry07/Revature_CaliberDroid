package com.revature.caliberdroid.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentGalleryBinding
import com.revature.caliberdroid.ui.batches.BatchesViewModel

class GalleryFragment : Fragment() {

    /* Constants */
    companion object {

    }

    /* variables */


    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = _binding!!
    private val batchesViewModel: BatchesViewModel by activityViewModels()
    //private val args : GalleryFragmentArgs by navArgs()
    private lateinit var batchSelect: Batch

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentGalleryBinding.inflate(layoutInflater)

        binding.apply {
            batchesViewModel = this@GalleryFragment.batchesViewModel
        }

        //batchSelect = args.batchSelected

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
