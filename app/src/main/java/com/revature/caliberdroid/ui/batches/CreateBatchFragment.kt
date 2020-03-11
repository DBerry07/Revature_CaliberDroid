package com.revature.caliberdroid.ui.batches

import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.databinding.FragmentCreateBatchBinding
import java.util.Observer


class CreateBatchFragment : Fragment() {

    private var _binding: FragmentCreateBatchBinding? = null
    private val binding
        get() = _binding!!
    private val args: CreateBatchFragmentArgs by navArgs()
    private var batch: Batch? = null
    private val viewModel: BatchesViewModel by activityViewModels()

    lateinit var trainingTypeAdapter: ArrayAdapter<CharSequence>
    private var trainerAdapter: ArrayAdapter<Trainer>? = null
    private var locationAdapter: ArrayAdapter<Location>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )

        // ViewModel Init
        viewModel.getLocations()
        viewModel.getTrainers()

        // Inflate the layout for this fragment
        _binding = FragmentCreateBatchBinding.inflate(layoutInflater)
        batch = args.selectedBatch

        binding.btnCreateBatchCancel.setOnClickListener{
            findNavController().navigate(CreateBatchFragmentDirections.actionCreateBatchFragmentToManageBatchFragment())
        }

        if(batch == null) {
            binding.btnCreateBatchCreate.text = getString(R.string.btn_editBatch_update)
            (activity as AppCompatActivity).supportActionBar?.title = "Create Batch"
        }

        // put this in spinnerInit()
        trainingTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.training_type_array, android.R.layout.simple_spinner_item)
        trainingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCreateBatchTrainingType.adapter = trainingTypeAdapter
        //

        //spinnerInit()

        binding.btnCreateBatchCreate.setOnClickListener {
            if(checkEmptyFields()){
                if(batch!=null) {
                    updateBatch()
                }
                else{
                    createBatch()
                }
            }
        }

        if(batch != null) {
            setExistingValues()
        }

        return binding.root
    }

    private fun checkEmptyFields(): Boolean {
        var result = true
        if(TextUtils.isEmpty(binding.etCreateBatchNameInput.text)) {
            binding.etCreateBatchNameInput.error = "Batch Name cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchSkillInput.text)) {
            binding.etCreateBatchSkillInput.error = "Skill focus cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchStartInput.text)) {
            binding.etCreateBatchStartInput.error = "Start date cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchEndInput.text)) {
            binding.etCreateBatchEndInput.error = "End date cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchGoodGradeInput.text)) {
            binding.etCreateBatchGoodGradeInput.error = "Good grade cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchPassingGradeInput.text)) {
            binding.etCreateBatchPassingGradeInput.error = "Passing grade cannot be empty"
            result = false
        }

        return result
    }


    private fun createBatch() {
        // creating an empty batch that will be filled with data from the edit text views
        batch = Batch(0,"","","","","",0,"",0,0,0,0,0)
        setBatchValues()
        BatchRepository.addBatch(batch!!)
    }

    private fun updateBatch() {
        setBatchValues()
        BatchRepository.editBatch(batch!!)
        Snackbar.make(view!!,"Batch Updated Successfully!", Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(CreateBatchFragmentDirections.actionCreateBatchFragmentToManageBatchFragment())
    }

    private fun setBatchValues() {
        batch?.trainingName = binding.etCreateBatchNameInput.text.toString()
        batch?.trainerName = binding.spinnerCreatebatchTrainer.selectedItem.toString()
        batch?.coTrainerName = binding.spinnerCreatebatchCotrainer.selectedItem.toString()
        batch?.location = binding.spinnerCreatebatchLocation.selectedItem.toString()
        batch?.skillType = binding.etCreateBatchSkillInput.text.toString()
        // convert back to timestamps for API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO: conversion is all messed up
            //batch._startDate = DateConverter.getTimestamp(startDate.text.toString())
            //batch._endDate = DateConverter.getTimestamp(endDate.text.toString())
        }
        // TODO
        batch?.trainingType = binding.spinnerCreateBatchTrainingType.selectedItem.toString()
        batch?.goodGrade = binding.etCreateBatchGoodGradeInput.text.toString().toInt()
        batch?.passingGrade = binding.etCreateBatchPassingGradeInput.text.toString().toInt()
    }

    private fun spinnerInit() {
        locationAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.locations.value!!
        )
        binding.spinnerCreatebatchLocation.adapter = locationAdapter!!

        /*
        trainerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.trainers.value!!
        )
        binding.spinnerCreatebatchTrainer.adapter = trainerAdapter!!

        binding.spinnerCreatebatchCotrainer.adapter = trainerAdapter!!

         */

    }

    private fun setExistingValues() {
        binding.etCreateBatchNameInput.setText(batch?.trainingName)
        binding.etCreateBatchSkillInput.setText(batch?.skillType)
        binding.etCreateBatchStartInput.setText(batch?.startDate)
        binding.etCreateBatchEndInput.setText(batch?.endDate)
        binding.etCreateBatchGoodGradeInput.setText(batch?.goodGrade.toString())
        binding.etCreateBatchPassingGradeInput.setText(batch?.passingGrade.toString())

        var spinnerPosition: Int = trainingTypeAdapter.getPosition(batch?.trainingType)
        binding.spinnerCreateBatchTrainingType.setSelection(spinnerPosition)

        // TODO: Uncomment after getting the api data
        /*

        spinnerPosition = locationAdapter.getPosition(batch?.location)
        binding.spinnerCreatebatchLocation.setSelection(spinnerPosition)

        spinnerPosition = trainerAdapter.getPosition(batch?.trainerName)
        binding.spinnerCreatebatchTrainer.setSelection(spinnerPosition)

        spinnerPosition = cotrainerAdapter.getPosition(batch?.coTrainerName)
        binding.spinnerCreatebatchCotrainer.setSelection(spinnerPosition)

        */

}


}
