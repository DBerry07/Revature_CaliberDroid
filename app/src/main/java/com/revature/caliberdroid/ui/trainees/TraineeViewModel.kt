package com.revature.caliberdroid.ui.trainees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.repository.TraineeRepository
import org.json.JSONObject


class TraineeViewModel : ViewModel() {

    lateinit var traineesLiveData: LiveData< List<Trainee> >
    val allBatchesLiveData: MutableLiveData< ArrayList<Batch> > = MutableLiveData()

    fun getTrainees(batchId : Long){
        traineesLiveData = TraineeRepository.getTrainees(batchId)
    }

    fun postTrainee(jsonObject: JSONObject){
        TraineeRepository.postTrainee(jsonObject)
    }

    fun getAllBatches(){
        TraineeRepository.getAllBatches(allBatchesLiveData)
    }

}