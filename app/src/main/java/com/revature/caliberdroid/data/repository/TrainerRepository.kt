package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Trainer

object TrainerRepository {

    fun getTrainers(): MutableLiveData< ArrayList<Trainer> >{
        val liveData = MutableLiveData< ArrayList<Trainer> >()
        APIHandler.getTrainers(liveData)
        return liveData
    }

    fun addTrainer(trainer:Trainer,liveData: MutableLiveData< ArrayList<Trainer> >){
        APIHandler.addTrainer(trainer, liveData)
    }
}