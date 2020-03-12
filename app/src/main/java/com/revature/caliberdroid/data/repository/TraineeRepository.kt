package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import org.json.JSONObject

object TraineeRepository {

    fun getTrainees(batchId: Long): MutableLiveData<List<Trainee>>{
        val liveData = MutableLiveData< List<Trainee> >()
        APIHandler.getTrainees(liveData, batchId)
        return liveData
    }

    fun postTrainee(jsonObject: JSONObject) {
        APIHandler.postTrainee(jsonObject)
    }

    fun putTrainee(jsonObject: JSONObject) {
        APIHandler.putTrainee(jsonObject)
    }

    //need this to switch trainees between batches
    fun getAllBatches(liveData: MutableLiveData<ArrayList<Batch>>){
        APIHandler.getAllBatches(liveData)
    }

    fun switchTrainee(traineeLiveData: MutableLiveData<Trainee>, newBatch: Batch) {
        APIHandler.switchTrainee(traineeLiveData, newBatch)
    }

}