package com.revature.caliberdroid.ui.batches

import android.app.ActionBar
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.databinding.FragmentCreateBatchBinding

class CreateBatchFragment : Fragment() {

    private var _binding: FragmentCreateBatchBinding? = null
    private val binding
        get() = _binding!!
    private val args: CreateBatchFragmentArgs by navArgs()
    private var batch: Batch? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val ab: ActionBar? = activity?.actionBar

        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )

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

        binding.btnCreateBatchCreate.setOnClickListener {
            if(batch!=null) {
                updateBatch()
            }
            else{
                createBatch()
            }
        }

        if(batch != null) {
            setExistingValues()
        }

        return binding.root
    }


    private fun createBatch() {
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
        batch?.trainerName = binding.etCreateBatchNameTrainerInput.text.toString()
        batch?.location = binding.etCreateBatchLocationInput.text.toString()
        batch?.skillType = binding.etCreateBatchSkillInput.text.toString()
        // convert back to timestamps for API
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // TODO: conversion is all messed up
            //batch._startDate = DateConverter.getTimestamp(startDate.text.toString())
            //batch._endDate = DateConverter.getTimestamp(endDate.text.toString())
        }
        batch?.trainingType = binding.etCreateBatchTrainingTypeInput.text.toString()
        batch?.goodGrade = binding.etCreateBatchGoodGradeInput.text.toString().toInt()
        batch?.passingGrade = binding.etCreateBatchPassingGradeInput.text.toString().toInt()
    }

    private fun setExistingValues() {
        binding.etCreateBatchNameTrainerInput.setText(batch?.trainerName)
        binding.etCreateBatchNameInput.setText(batch?.trainingName)
        binding.etCreateBatchLocationInput.setText(batch?.location)
        binding.etCreateBatchSkillInput.setText(batch?.skillType)
        binding.etCreateBatchStartInput.setText(batch?.startDate)
        binding.etCreateBatchEndInput.setText(batch?.endDate)
        binding.etCreateBatchGoodGradeInput.setText(batch?.goodGrade.toString())
        binding.etCreateBatchPassingGradeInput.setText(batch?.passingGrade.toString())
        binding.etCreateBatchTrainingTypeInput.setText(batch?.trainingType)
    }


}
