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
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.databinding.FragmentEditTrainerBinding
import kotlinx.android.synthetic.main.include_trainer_fields.*

class EditTrainerFragment : Fragment() {
    private var _binding: FragmentEditTrainerBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()
    private var trainer: Trainer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditTrainerBinding.inflate(layoutInflater)
        val context = getContext()!!
        binding.apply {
            trainersViewModel.selectedTrainerLiveData.observe(viewLifecycleOwner) { trainer->

                var list_of_items = arrayOf(
                    "ROLE_INACTIVE",
                    "ROLE_QC",
                    "ROLE_TRAINER",
                    "ROLE_VP",
                    "ROLE_PANEL",
                    "ROLE_STAGING"
                )

                etFullName.setText(trainer.name)
                etEmail.setText(trainer.email)
                etTitle.setText(trainer.title)
                val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list_of_items)
                spnTier.adapter = adapter
            }
        }

        return binding.root
    }

}
