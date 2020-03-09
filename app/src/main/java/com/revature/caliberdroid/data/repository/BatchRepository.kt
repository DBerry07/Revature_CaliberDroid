package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData

object BatchRepository {

    fun getBatches(): LiveData<List<Batch>> {

        val liveData = MutableLiveData<List<Batch>>()

        APIHandler.getBatches(liveData)

        return liveData
    }

    fun addWeekFromAudit(batch: Batch, listLiveData: MutableLiveData<ArrayList<WeekLiveData>>) {
        APIHandler.addWeekFromAudit(batch, listLiveData)
    }

    fun addWeekFromAssess(batch: Batch, listLiveData: MutableLiveData<ArrayList<AssessWeekLiveData>>) {
        APIHandler.addWeekFromAssess(batch, listLiveData)
    }

    fun getValidYears(): LiveData<List<Int>> {
        val liveData = MutableLiveData<List<Int>>()

        liveData.value = ArrayList()

        APIHandler.getValidYears(liveData)

        return liveData
    }

}