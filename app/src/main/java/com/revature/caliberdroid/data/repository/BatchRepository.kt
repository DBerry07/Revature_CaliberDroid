package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Batch

object BatchRepository {

    fun getBatches(): LiveData<Batch> {

        val liveData = MutableLiveData<Batch>()

        APIHandler.getBatches(liveData)

        return liveData
    }

}