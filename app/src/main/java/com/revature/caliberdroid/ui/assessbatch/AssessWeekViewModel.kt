package com.revature.caliberdroid.ui.assessbatch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Note
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.repository.AssessWeekRepository
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData
import timber.log.Timber


class AssessWeekViewModel : ViewModel() {

    lateinit var assessWeekNotes: AssessWeekNotes
    lateinit var batchAssessWeekNotes: MutableLiveData<ArrayList<AssessWeekLiveData>>
    lateinit var trainees: MutableLiveData<List<Trainee>>
    lateinit var batch: Batch
    lateinit var saveNoteThread: Thread

//    fun initWeekData() {
//        var batchId:Long = assessWeekNotes.batch!!.batchID
//        var weekNumber:Int = assessWeekNotes.weekNumber
//        assessWeekNotes = AssessWeekRepository.getAssessWeekData(batchId,weekNumber)
//    }

    fun addWeek() {
        BatchRepository.addWeekFromAssess(batch, batchAssessWeekNotes)
        startDelayedSaveThread(Note(), this::saveBatchNote)
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
        Timber.d("saving note")
        saveNoteThread.interrupt()
    }

    fun startDelayedSaveThread(note: Note, saveFunction: (note: Note) -> Unit) {
        when (saveNoteThread.state) {

            Thread.State.NEW -> {
                Timber.d("new thread")
                saveNoteThread = getNewDelayedSaveThread(note, saveFunction)
                saveNoteThread.start()
            }

            Thread.State.TERMINATED -> {
                Timber.d("finished")
                saveNoteThread = getNewDelayedSaveThread(note, saveFunction)
                saveNoteThread.start()
            }

            else -> {
                Timber.d("running")
                saveNoteThread.interrupt()
                saveNoteThread = getNewDelayedSaveThread(note, saveFunction)
                saveNoteThread.start()
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
