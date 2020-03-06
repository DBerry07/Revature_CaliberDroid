package com.revature.caliberdroid.ui.trainers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentAddTrainerBinding
import kotlinx.android.synthetic.main.include_trainer_fields.*


class AddTrainerFragment : Fragment() {
    private var _binding: FragmentAddTrainerBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTrainerBinding.inflate(layoutInflater)
        val context = context!!
        binding.apply {
            trainersViewModel.selectedTrainerLiveData.observe(viewLifecycleOwner) { trainer->

                val list_of_items = arrayOf(
                    "ROLE_INACTIVE",
                    "ROLE_QC",
                    "ROLE_TRAINER",
                    "ROLE_VP",
                    "ROLE_PANEL",
                    "ROLE_STAGING"
                )
                val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list_of_items)
                spnTier.adapter = adapter
            }
        }
        return binding.root
    }

}
