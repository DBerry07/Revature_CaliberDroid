package com.revature.caliberdroid.ui.qualityaudit.trainees

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AuditTraineeWithNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.repository.QualityAuditRepository
import timber.log.Timber

class QualityAuditTraineesViewModel : ViewModel() {

    lateinit var traineesWithNotesLiveData: MutableLiveData<List<TraineeWithNotesLiveData>>
    var saveAuditWeekNotesThread: Thread? = null

    fun getTraineesWithNotes(batch: Batch, weekNumber: Int) {
        traineesWithNotesLiveData = QualityAuditRepository.getTraineesWithNotes(batch, weekNumber)
    }

    fun putTraineeNotes(traineeWithNotes: AuditTraineeWithNotes) {
        saveAuditWeekNotesThread?.interrupt()
        Timber.d("Saving  Note")
        QualityAuditRepository.putTraineeWithNotes(traineeWithNotes)
    }

    fun putTrainee(trainee: Trainee) {
        QualityAuditRepository.putTrainee(trainee)
    }

    fun startDelayedSaveThread(
        traineeWithNotes: AuditTraineeWithNotes,
        saveFunction: (traineeWithNotes: AuditTraineeWithNotes) -> Unit
    ) {

        if (saveAuditWeekNotesThread == null) saveAuditWeekNotesThread =
            getNewDelayedSaveThread(traineeWithNotes, saveFunction)

        when (saveAuditWeekNotesThread!!.state) {

            Thread.State.NEW -> {
                Timber.d("new thread")
                saveAuditWeekNotesThread!!.start()
            }

            Thread.State.TERMINATED -> {
                Timber.d("finished")
                saveAuditWeekNotesThread = getNewDelayedSaveThread(traineeWithNotes, saveFunction)
                saveAuditWeekNotesThread!!.start()
            }

            else -> {
                Timber.d("running")
                saveAuditWeekNotesThread!!.interrupt()
                saveAuditWeekNotesThread = getNewDelayedSaveThread(traineeWithNotes, saveFunction)
                saveAuditWeekNotesThread!!.start()
            }
        }
    }

    fun getNewDelayedSaveThread(
        traineeWithNotes: AuditTraineeWithNotes,
        save: (traineeWithNotes: AuditTraineeWithNotes) -> Unit
    ): Thread {

        val t: Thread = object : Thread() {
            override fun run() {
                super.run()

                try {
                    sleep(5000)
                    save(traineeWithNotes)
                } catch (e: InterruptedException) {
                    Timber.d("stopped thread")
                    return
                }
            }
        }
        return t
    }

}