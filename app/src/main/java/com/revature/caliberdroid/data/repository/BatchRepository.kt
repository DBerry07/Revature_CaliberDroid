package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData

object BatchRepository {

    fun getBatches(): LiveData<List<Batch>> {

        val liveData = MutableLiveData<List<Batch>>()

        APIHandler.getBatches(liveData)

        return liveData
    }

    fun getAuditWeekNotes(batch: Batch): LiveData<ArrayList<WeekLiveData>> {

        val liveData = MutableLiveData<ArrayList<WeekLiveData>>()

        liveData.value = ArrayList()

        for (i in 1 .. batch.weeks) {
            liveData.value?.add(WeekLiveData().apply {
                value = AuditWeekNotes(i, null, null)
            })
        }

        APIHandler.getAuditWeekNotes(liveData, batch)

        return liveData
    }

}