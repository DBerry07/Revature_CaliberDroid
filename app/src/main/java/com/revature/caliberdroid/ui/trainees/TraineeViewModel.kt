package com.revature.caliberdroid.ui.trainees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.repository.TraineeRepository
import org.json.JSONObject


class TraineeViewModel : ViewModel() {

    lateinit var traineesLiveData: LiveData< List<Trainee> >

    fun getTrainees(batchId : Long){
        traineesLiveData = TraineeRepository.getTrainees(batchId)
    }

    fun postTrainee(jsonObject: JSONObject){
        TraineeRepository.postTrainee(jsonObject)
    }

    fun putTrainee(jsonObject: JSONObject){
        TraineeRepository.putTrainee(jsonObject)
    }

}