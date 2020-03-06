package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.*

object AssessWeekRepository {

    fun getAssessBatchOverallNote(batchId: Long,weekNumber: Int): MutableLiveData<Note> {
        val liveData = MutableLiveData<Note>()

        APIHandler.getAssessBatchOverallNote(liveData,batchId,weekNumber)

        return liveData
    }

    fun getAssessWeekData(batchId: Long,weekNumber: Int): MutableLiveData<AssessWeekNotes> {
        var assessWeekNotes = AssessWeekNotes(weekNumber,0f,"",null, getAssessments(batchId,weekNumber),
            getGrades(batchId,weekNumber), getTraineeNotes(batchId,weekNumber))
        var liveData = MutableLiveData<AssessWeekNotes>()
        liveData.value = assessWeekNotes
        return liveData
    }

    fun getAssessments(batchId:Long,weekNumber:Int): MutableLiveData<List<Assessment>> {
        val liveData = MutableLiveData<List<Assessment>>()

        APIHandler.getAssessments(liveData,batchId,weekNumber)

        return liveData
    }

    fun getGrades(batchId:Long,weekNumber:Int): MutableLiveData<List<Grade>> {
        val liveData = MutableLiveData<List<Grade>>()

        APIHandler.getGrades(liveData,batchId,weekNumber)

        return liveData
    }

    fun getTraineeNotes(batchId:Long,weekNumber:Int): MutableLiveData<List<Note>> {
        val liveData = MutableLiveData<List<Note>>()

        APIHandler.getTraineeNotes(liveData,batchId,weekNumber)

        return liveData
    }

    fun getTrainees(batchId: Long): MutableLiveData<List<Trainee>> {
        val liveData = MutableLiveData<List<Trainee>>()

        APIHandler.getTrainees(liveData,batchId)

        return liveData
    }

}