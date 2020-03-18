package com.revature.caliberdroid.ui.assessbatch

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.repository.AssessWeekRepository
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.data.repository.CategoryRepository
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData
import timber.log.Timber
import kotlin.math.round


class AssessWeekViewModel : ViewModel() {

    lateinit var assessWeekNotes: AssessWeekNotes
    lateinit var batchAssessWeekNotes: MutableLiveData<ArrayList<AssessWeekLiveData>>
    lateinit var trainees: MutableLiveData<List<Trainee>>
    lateinit var batch: Batch
    var saveNoteThread: Thread? = null
    var categories: MutableLiveData<ArrayList<Category>> = MutableLiveData(arrayListOf())

//    fun initWeekData() {
//        var batchId:Long = assessWeekNotes.batch!!.batchID
//        var weekNumber:Int = assessWeekNotes.weekNumber
//        assessWeekNotes = AssessWeekRepository.getAssessWeekData(batchId,weekNumber)
//    }

    fun addWeek() {
        BatchRepository.addWeekFromAssess(batch, batchAssessWeekNotes)
        startDelayedSaveThread(Note(), this::saveBatchNote)
    }

    fun createAssessmentForBatchWeek(assessment: Assessment) {
        assessment.batchId = batch.batchID
        assessment.weekNumber = assessWeekNotes.weekNumber
        val liveDataAssessment = MutableLiveData(assessment)
        AssessWeekRepository.createAssessment(liveDataAssessment)
        liveDataAssessment.observeForever(Observer {
            if (assessWeekNotes.weekNumber == it.weekNumber && assessWeekNotes.batch!!.batchID == it.batchId) {
                (assessWeekNotes.assessments as ArrayList).add(it)
            }
        })
    }

    fun deleteAssessment(assessment: Assessment) {
        (assessWeekNotes.assessments as ArrayList).remove(assessment)
        AssessWeekRepository.deleteAssessment(assessment)
    }

    fun findCategoryById(id: Long): Category {
        var returnCategory = Category(-1,"none", false)

        for (category in categories.value!!) {
            if (category.categoryId == id) returnCategory = category
        }

        return returnCategory
    }

    fun getSkills(): MutableLiveData<ArrayList<Category>> {

        if (categories.value!!.size == 0) {
            categories = CategoryRepository.getCategories()
        }
        return categories
    }

    fun loadBatchWeeks(batch: Batch) {
        this.batch = batch
        batchAssessWeekNotes = AssessWeekRepository.getBatchAssessWeekNotes(batch)
    }

    fun loadTrainees() {
        trainees = AssessWeekRepository.getTrainees(assessWeekNotes.batch!!.batchID)
        assessWeekNotes.traineeNotes = AssessWeekRepository.getTraineeNotes(assessWeekNotes.batch!!.batchID,assessWeekNotes.weekNumber)
    }

    fun saveBatchNote(note: Note) {
        if (saveNoteThread != null) saveNoteThread!!.interrupt()
        Timber.d("saving note")
        assessWeekNotes.batchNote.noteContent = note.noteContent
        AssessWeekRepository.saveBatchNote(assessWeekNotes.batchNote)
    }

    fun saveTraineeNote(note: Note) {
        if(saveNoteThread != null) saveNoteThread!!.interrupt()
        AssessWeekRepository.putTraineeNote(note)
    }

    fun startDelayedSaveThread(note: Note, saveFunction: (note: Note) -> Unit) {

        if (saveNoteThread == null) saveNoteThread = getNewDelayedSaveThread(note, saveFunction)

        when (saveNoteThread!!.state) {

            Thread.State.NEW -> {
                Timber.d("new thread")
                saveNoteThread!!.start()
            }

            Thread.State.TERMINATED -> {
                Timber.d("finished")
                saveNoteThread = getNewDelayedSaveThread(note, saveFunction)
                saveNoteThread!!.start()
            }

            else -> {
                Timber.d("running")
                saveNoteThread!!.interrupt()
                saveNoteThread = getNewDelayedSaveThread(note, saveFunction)
                saveNoteThread!!.start()
            }
        }
    }

    fun getNewDelayedSaveThread(note: Note, save: (note: Note) -> Unit): Thread {

        var t: Thread = object: Thread() {
            override fun run() {
                super.run()

                try {
                    sleep(5000)
                    save(note)
                } catch (e: InterruptedException) {
                    Timber.d("stopped thread")
                    return
                }
            }
        }
        return t
    }

    fun getAssessmentAverage(assessment: Assessment): Double {
        var totalPoints = 0.0
        var totalPossible = 0.0
        for(grade in assessWeekNotes.grades) {
            if(grade.assessmentId==assessment.assessmentId) {
                totalPoints += grade.score!!
                totalPossible += assessment.rawScore!!
            }
        }
        return if(((totalPoints/totalPossible)*100).round().isNaN()) 0.0 else ((totalPoints/totalPossible)*100).round()
    }

    fun getWeeklyBatchAverage(assessWeekNotes: AssessWeekNotes): Double {
        var avgAssessment = 0.0
        var totalAssessment = 0.0
        for(assessment in assessWeekNotes.assessments){
            avgAssessment+=(getAssessmentAverage(assessment)/100.0*assessment.rawScore!!)
            totalAssessment+=assessment.rawScore!!
        }
        var batchAvg = (avgAssessment/totalAssessment).round()
        assessWeekNotes.batchAverage=batchAvg.toFloat()
        return batchAvg
    }

    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

}
