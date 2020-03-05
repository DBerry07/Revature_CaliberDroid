package com.revature.caliberdroid.ui.trainers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.categories.listeners.EditTrainerInterface
import com.revature.caliberdroid.adapter.trainers.TrainersAdapter
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.databinding.FragmentTrainersBinding
import kotlinx.android.synthetic.main.fragment_trainers.*

class TrainersFragment : Fragment(){
    private var _binding: FragmentTrainersBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()
    private var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View?{
        navController = findNavController()
        trainersViewModel.getTrainers()
        _binding = FragmentTrainersBinding.inflate(layoutInflater)
        binding.apply {
            setLifecycleOwner (this@TrainersFragment )
            trainersViewModel.trainerLiveData.observe(viewLifecycleOwner, Observer { trainers->
                rvTrainers.adapter = TrainersAdapter(trainers, EditTrainerListener())
            })
            btnAddTrainer.setOnClickListener{
                findNavController().navigate(R.id.action_trainersFragment_to_addTrainerFragment)
            }
        }

        return binding.root
    }

    inner class EditTrainerListener: EditTrainerInterface{
        override fun onEditTrainer(trainer: Trainer) {
            trainersViewModel.selectedTrainerLiveData.value = trainer
            navController?.navigate(R.id.action_trainersFragment_to_editTrainerFragment)
        }
    }
}