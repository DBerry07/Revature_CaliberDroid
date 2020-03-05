package com.revature.caliberdroid.ui.trainers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentTrainersBinding
import kotlinx.android.synthetic.main.fragment_trainers.*

class TrainersFragment : Fragment(){
    private var _binding: FragmentTrainersBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View?{
        _binding = FragmentTrainersBinding.inflate(layoutInflater)
        binding.apply {
            btnAddTrainer.setOnClickListener{
                findNavController().navigate(R.id.action_trainersFragment_to_addTrainerFragment)
            }
            btnEditTrainer.setOnClickListener{
                findNavController().navigate(R.id.action_trainersFragment_to_editTrainerFragment)
            }
        }

        return binding.root
    }
}