package com.revature.caliberdroid.ui.assessbatch

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.data.repository.AssessWeekRepository
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.data.repository.CategoryRepository
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData
import timber.log.Timber


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
        AssessWeekRepository.createAssessment(assessment)
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

}
