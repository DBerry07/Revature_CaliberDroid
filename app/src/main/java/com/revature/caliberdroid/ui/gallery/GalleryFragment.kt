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

const val name = ""

class GalleryFragment : Fragment() {

    /* Constants */
    companion object {

    }

    /* variables */


    private lateinit var binding : FragmentGalleryBinding
    private val galleryViewModel: GalleryViewModel by activityViewModels()
    private val args : GalleryFragmentArgs by navArgs()
    private lateinit var batchSelect: Batch

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = FragmentGalleryBinding.inflate(layoutInflater)

        binding.galleryViewModel = galleryViewModel

        batchSelect = args.batchSelected

        return binding.root
    }
}
