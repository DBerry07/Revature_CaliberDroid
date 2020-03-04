package com.revature.caliberdroid.ui.trainers

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.databinding.FragmentEditTrainerBinding

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
        trainersViewModel.selectedTrainerLiveData.observe(viewLifecycleOwner,{trainer->
            Log.d("Trainers",trainer.toString())
        })
        binding.apply {
        }

        return binding.root
    }

}
