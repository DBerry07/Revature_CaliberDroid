package com.revature.caliberdroid.ui.trainers

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.revature.caliberdroid.R

import com.revature.caliberdroid.adapter.SettingsSpinnerItemAdapter
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.repository.TrainerRepository
import com.revature.caliberdroid.databinding.FragmentSettingsAddTrainerBinding
import com.revature.caliberdroid.util.DialogInvalidInput
import timber.log.Timber


class AddTrainerFragment : Fragment() {
    private var _binding: FragmentSettingsAddTrainerBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()

    private var selectedTier:String = ""
    private val validationString = StringBuilder()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsAddTrainerBinding.inflate(layoutInflater)
        val context = context!!
        val listOfTiers = resources.getStringArray(R.array.trainer_tiers)
        binding.apply {
            val tiersSpinnerAdapter = SettingsSpinnerItemAdapter(
                context,
                listOfTiers
            )
            val spinner: Spinner = inTrainerFields.spnTier
            spinner.adapter = tiersSpinnerAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,view: View?,position: Int,id: Long) {
                    Timber.d("Item selected: ${listOfTiers.get(position)}")
                    selectedTier = listOfTiers.get(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
            btnAddTrainer.setOnClickListener {
                if(
                    TrainersFieldValidator.validateFields(
                        validationString,
                        inTrainerFields.etFullName,
                        inTrainerFields.etEmail,
                        inTrainerFields.etTitle
                    )
                ){
                    val trainerToCreate = Trainer(
                        inTrainerFields.etFullName.text.toString(),
                        inTrainerFields.etTitle.text.toString(),
                        inTrainerFields.etEmail.text.toString(),
                        selectedTier,
                        ""
                    )
                    Timber.d("New trainer: ${trainerToCreate.toString()}")
                    TrainersViewModel.addTrainer(trainerToCreate)
                }else{
                    Timber.d("Validation of fields failed: "+validationString.toString())
                    DialogInvalidInput().showInvalidInputDialog(context,view,validationString.toString())
                }
            }
        }
        return binding.root
    }

}
