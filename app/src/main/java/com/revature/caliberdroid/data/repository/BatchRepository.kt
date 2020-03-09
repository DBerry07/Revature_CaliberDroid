package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData

object BatchRepository {

    fun getBatchesByYearAndQuarter(selectedYear: Int): LiveData<List<Batch>> {

        val liveData = MutableLiveData<List<Batch>>()

        APIHandler.getBatchesByYear(liveData, selectedYear)

        return liveData
    }

    fun getBatchesByYearAndQuarter(
        liveData: MutableLiveData<List<Batch>>,
        selectedYear: Int,
        selectedQuarter: Int
    ) {
        APIHandler.getBatchesByYearAndQuarter(liveData, selectedYear, selectedQuarter)
    }

    fun addWeek(batch: Batch, listLiveData: MutableLiveData<ArrayList<WeekLiveData>>) {
        APIHandler.addWeek(batch, listLiveData)
    }

    fun getValidYears(): LiveData<List<Int>> {
        val liveData = MutableLiveData<List<Int>>()

        liveData.value = ArrayList()

        APIHandler.getValidYears(liveData)

        return liveData
    }

}