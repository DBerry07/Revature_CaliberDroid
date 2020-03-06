package com.revature.caliberdroid.ui.trainers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.revature.caliberdroid.R

import com.revature.caliberdroid.adapter.trainers.TiersAdapter
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.repository.TrainerRepository
import com.revature.caliberdroid.databinding.FragmentSettingsAddTrainerBinding
import timber.log.Timber


class AddTrainerFragment : Fragment() {
    private var _binding: FragmentSettingsAddTrainerBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()

    private var selectedTier:String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsAddTrainerBinding.inflate(layoutInflater)
        val context = getContext()!!
        val list_of_items = resources.getStringArray(R.array.trainer_tiers)
        binding.apply {
            val adapter = TiersAdapter(context, list_of_items)
            val spinner: Spinner = inTrainerFields.spnTier
            spinner.adapter = adapter
            spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,view: View?,position: Int,id: Long) {
                    Timber.d("Item selected: ${list_of_items.get(position)}")
                    selectedTier = list_of_items.get(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            })
            btnAddTrainer.setOnClickListener {
                val trainerToCreate = Trainer(
                    inTrainerFields.etFullName.text.toString(),
                    inTrainerFields.etTitle.text.toString(),
                    inTrainerFields.etEmail.text.toString(),
                    selectedTier,
                    ""
                )
                Timber.d("New trainer: ${trainerToCreate.toString()}")
                TrainerRepository.addTrainer(trainerToCreate)
                findNavController().navigateUp()
            }
        }
        return binding.root
    }

}
