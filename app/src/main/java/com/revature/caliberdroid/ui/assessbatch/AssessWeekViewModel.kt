package com.revature.caliberdroid.ui.assessbatch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.repository.AssessWeekRepository
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData

class AssessWeekViewModel : ViewModel() {

    lateinit var assessWeekNotes: AssessWeekNotes
    lateinit var batchAssessWeekNotes: MutableLiveData<ArrayList<AssessWeekLiveData>>
    lateinit var trainees: MutableLiveData<List<Trainee>>
    lateinit var batch: Batch

//    fun initWeekData() {
//        var batchId:Long = assessWeekNotes.batch!!.batchID
//        var weekNumber:Int = assessWeekNotes.weekNumber
//        assessWeekNotes = AssessWeekRepository.getAssessWeekData(batchId,weekNumber)
//    }

    fun loadBatchWeeks(batch: Batch) {
        this.batch = batch
        batchAssessWeekNotes = AssessWeekRepository.getBatchAssessWeekNotes(batch)
    }

    fun loadTrainees() {
        trainees = AssessWeekRepository.getTrainees(assessWeekNotes.batch!!.batchID)
        assessWeekNotes.traineeNotes = AssessWeekRepository.getTraineeNotes(assessWeekNotes.batch!!.batchID,assessWeekNotes.weekNumber)
    }

    fun addWeek() {
        BatchRepository.addWeekFromAssess(batch, batchAssessWeekNotes)
    }

}
