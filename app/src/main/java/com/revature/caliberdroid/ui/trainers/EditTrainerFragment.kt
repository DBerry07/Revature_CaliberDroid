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
import com.revature.caliberdroid.adapter.SettingsSpinnerItemAdapter
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.repository.TrainerRepository
import com.revature.caliberdroid.databinding.FragmentSettingsEditTrainerBinding
import kotlinx.android.synthetic.main.include_trainer_fields.*
import timber.log.Timber

class EditTrainerFragment : Fragment() {
    private var _binding: FragmentSettingsEditTrainerBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()
    private lateinit var trainer: Trainer
    private var selectedTier: String = ""
    private val validationString = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsEditTrainerBinding.inflate(layoutInflater)
        trainer = trainersViewModel.selectedTrainerLiveData.value!!
        val context = getContext()!!
        val list_of_items = resources.getStringArray(R.array.trainer_tiers)
        binding.apply {
            val adapter = SettingsSpinnerItemAdapter(
                context,
                list_of_items
            )

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

            inTrainerFields.etFullName.setText(trainer.name)
            inTrainerFields.etEmail.setText(trainer.email)
            inTrainerFields.etTitle.setText(trainer.title)
            Timber.d("This is the selected trainer's tier: "+trainer.tier)
            for(i in 0 until inTrainerFields.spnTier.count){
                Timber.d("Running through list of spinner: "+inTrainerFields.spnTier.getItemAtPosition(i).toString())
                if( inTrainerFields.spnTier.getItemAtPosition(i).equals(trainer.tier) ){
                    inTrainerFields.spnTier.setSelection(i)
                }
            }

            btnEditTrainer.setOnClickListener {
                if(
                    TrainersFieldValidator.validateFields(
                        validationString,
                        inTrainerFields.etFullName,
                        inTrainerFields.etEmail,
                        inTrainerFields.etTitle
                    )
                ){
                    trainer.name = etFullName.text.toString()
                    trainer.email = etEmail.text.toString()
                    trainer.title = etTitle.text.toString()
                    trainer.tier = selectedTier

                    Timber.d("Updated trainer: ${trainer.toString()}")
                    TrainerRepository.editTrainer(trainer)

                    findNavController().navigateUp()
                }else{
                    Timber.d("Validation of fields failed: "+validationString.toString())
                }
            }
        }

        return binding.root
    }

}
