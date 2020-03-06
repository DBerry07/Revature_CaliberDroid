package com.revature.caliberdroid.ui.trainers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.repository.TrainerRepository

class TrainersViewModel : ViewModel() {
    lateinit var trainersLiveData: MutableLiveData< ArrayList<Trainer> >
    val selectedTrainerLiveData = MutableLiveData< Trainer >()

    fun getTrainers(){
        trainersLiveData = TrainerRepository.getTrainers()
    }

    fun setSelectedTrainer(trainer:Trainer){
        selectedTrainerLiveData.value = trainer
    }
}