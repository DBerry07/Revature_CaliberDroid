package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData

object AssessWeekRepository {

    fun getAssessWeekData(batch: Batch,weekNumber: Int): AssessWeekLiveData {
        var assessWeekNotes = AssessWeekNotes(weekNumber,batch)

        APIHandler.getAssessWeekNotes(assessWeekNotes)

        var liveData = AssessWeekLiveData()

        liveData.value = assessWeekNotes
        return liveData
    }


    fun getBatchAssessWeekNotes(batch:Batch):MutableLiveData<ArrayList<AssessWeekLiveData>> {
        var liveData = MutableLiveData<ArrayList<AssessWeekLiveData>>()

        liveData.value = ArrayList()

        for(i in 1..batch.weeks) {
            liveData.value!!.add(getAssessWeekData(batch,i))
        }

        return liveData
    }

    fun getTraineeNotes(batchId:Long,weekNumber:Int): MutableLiveData<List<Note>> {
        val liveData = MutableLiveData<List<Note>>()
        liveData.postValue(arrayListOf())

        APIHandler.getTraineeNotes(liveData,batchId,weekNumber)

        return liveData
    }

    fun getTrainees(batchId: Long): MutableLiveData<List<Trainee>> {
        val liveData = MutableLiveData<List<Trainee>>()
        liveData.postValue(arrayListOf())

        APIHandler.getTrainees(liveData,batchId)

        return liveData
    }

    fun putTraineeNote(note: Note) {
        APIHandler.putTraineeNote(note)
    }

    fun saveBatchNote(note: Note) {
        APIHandler.putAssessBatchOverallNote(note)
    }

}