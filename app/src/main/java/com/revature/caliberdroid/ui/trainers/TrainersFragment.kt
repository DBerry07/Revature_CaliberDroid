package com.revature.caliberdroid.ui.trainers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.trainers.listeners.EditTrainerInterface
import com.revature.caliberdroid.adapter.trainers.TrainersAdapter
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.databinding.FragmentSettingsTrainersBinding
import java.util.ArrayList

class TrainersFragment : Fragment(){
    private var _binding: FragmentSettingsTrainersBinding? = null
    private val binding get() = _binding!!
    private val trainersViewModel: TrainersViewModel by activityViewModels()
    private var navController: NavController? = null
    lateinit var rvAdapter: TrainersAdapter
    var trainersFromAPI = ArrayList<Trainer>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View?{
        navController = findNavController()
        trainersViewModel.getTrainers()
        _binding = FragmentSettingsTrainersBinding.inflate(layoutInflater)
        binding.apply {
            setLifecycleOwner (this@TrainersFragment )

            trainersViewModel.trainersLiveData.observe(viewLifecycleOwner, Observer { trainers->
                trainersFromAPI = trainers
                rvAdapter = TrainersAdapter(EditTrainerListener())
                rvAdapter.sortedList.addAll(trainersFromAPI)
                rvTrainers.adapter = rvAdapter
            })

            btnAddTrainer.setOnClickListener{
                findNavController().navigate(R.id.action_trainersFragment_to_addTrainerFragment)
            }

            searchViewTrainers.setOnQueryTextListener(object: SearchView.OnQueryTextListener,
                android.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {return false}

                override fun onQueryTextChange(newText: String?): Boolean {
                    rvAdapter.replaceAll( filterLocations(trainersFromAPI, newText) )
                    return true
                }

            })
        }

        return binding.root
    }

    inner class EditTrainerListener:
        EditTrainerInterface {
        override fun onEditTrainer(trainer: Trainer) {
            trainersViewModel.selectedTrainerLiveData.value = trainer
            navController?.navigate(R.id.action_trainersFragment_to_editTrainerFragment)
        }
    }

    fun filterLocations(trainers:ArrayList<Trainer> , _query:String?): ArrayList<Trainer>{
        val filteredTrainers: ArrayList<Trainer> = ArrayList()
        if(_query != null){
            val query = _query.toLowerCase()
            for(i in 0 until trainers.size){
                val current = trainers.get(i)
                if(current.name.toLowerCase().contains(query)){
                    filteredTrainers.add(current)
                }
            }
        }
        return filteredTrainers
    }
}