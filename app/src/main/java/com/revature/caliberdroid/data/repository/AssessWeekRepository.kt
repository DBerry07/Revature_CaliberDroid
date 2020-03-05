package com.revature.caliberdroid.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.*

object AssessWeekRepository {

    fun getAssessBatchOverallNote(batchId: Long,weekNumber: Int): LiveData<Note> {
        val liveData = MutableLiveData<Note>()

        APIHandler.getAssessBatchOverallNote(liveData,batchId,weekNumber)

        return liveData
    }

    fun getAssessments(batchId:Long,weekNumber:Int): LiveData<List<Assessment>> {
        val liveData = MutableLiveData<List<Assessment>>()

        APIHandler.getAssessments(liveData,batchId,weekNumber)

        return liveData
    }

    fun getGrades(batchId:Long,weekNumber:Int): LiveData<List<Grade>> {
        val liveData = MutableLiveData<List<Grade>>()

        APIHandler.getGrades(liveData,batchId,weekNumber)

        return liveData
    }

    fun getTraineeNotes(batchId:Long,weekNumber:Int): LiveData<List<Note>> {
        val liveData = MutableLiveData<List<Note>>()

        APIHandler.getTraineeNotes(liveData,batchId,weekNumber)

        return liveData
    }

    fun getTrainees(batchId: Long): LiveData<List<Trainee>> {
        val liveData = MutableLiveData<List<Trainee>>()

        APIHandler.getTrainees(liveData,batchId)

        return liveData
    }

}