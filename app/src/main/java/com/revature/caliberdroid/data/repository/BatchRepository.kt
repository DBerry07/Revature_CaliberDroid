package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch

object BatchRepository {

    fun getBatches(): LiveData<List<Batch>> {

        val liveData = MutableLiveData<List<Batch>>()

        APIHandler.getBatches(liveData)

        return liveData
    }

    fun getAuditWeekNotes(batch: Batch, liveData: MutableLiveData<ArrayList<AuditWeekNotes>>) /*LiveData<ArrayList<WeekLiveData>>*/ {
        APIHandler.getAuditWeekNotes(liveData, batch)
    }

    fun addWeek(batch: Batch, listLiveData: MutableLiveData<ArrayList<AuditWeekNotes>>) {
        APIHandler.addWeek(batch, listLiveData)
    }

    fun addBatch(batch: Batch){
        APIHandler.addBatch(batch)
    }

    fun editBatch(batch: Batch){
        APIHandler.editBatch(batch)
    }

    fun deleteBatch(batch: Batch){
        APIHandler.deleteBatch(batch)
    }

}