package com.revature.caliberdroid.ui.qualityaudit.overall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.repository.QualityAuditRepository
import timber.log.Timber

class QualityAuditOverallViewModel : ViewModel() {

    val skillCategoryLiveData: LiveData<List<SkillCategory>> = MutableLiveData()
    var saveAuditWeekNotesThread: Thread? = null

    fun getSkillCategories(batch: Batch, weekNumber: Int) {
        QualityAuditRepository.getSkillCategories(skillCategoryLiveData as MutableLiveData, batch, weekNumber)
    }

    fun putAuditWeekNotes(auditWeekNotes: AuditWeekNotes) {
        saveAuditWeekNotesThread?.interrupt()
        Timber.d("Saving Audit Week Note")
        QualityAuditRepository.putAuditWeekNotes(auditWeekNotes)
    }

    fun startDelayedSaveThread(
        auditWeekNotes: AuditWeekNotes,
        saveFunction: (auditWeekNotes: AuditWeekNotes) -> Unit
    ) {

        if (saveAuditWeekNotesThread == null) saveAuditWeekNotesThread =
            getNewDelayedSaveThread(auditWeekNotes, saveFunction)

        when (saveAuditWeekNotesThread!!.state) {

            Thread.State.NEW -> {
                Timber.d("new thread")
                saveAuditWeekNotesThread!!.start()
            }

            Thread.State.TERMINATED -> {
                Timber.d("finished")
                saveAuditWeekNotesThread = getNewDelayedSaveThread(auditWeekNotes, saveFunction)
                saveAuditWeekNotesThread!!.start()
            }

            else -> {
                Timber.d("running")
                saveAuditWeekNotesThread!!.interrupt()
                saveAuditWeekNotesThread = getNewDelayedSaveThread(auditWeekNotes, saveFunction)
                saveAuditWeekNotesThread!!.start()
            }
        }
    }

    fun getNewDelayedSaveThread(
        auditWeekNotes: AuditWeekNotes,
        save: (auditWeekNotes: AuditWeekNotes) -> Unit
    ): Thread {

        val t: Thread = object : Thread() {
            override fun run() {
                super.run()

                try {
                    sleep(5000)
                    save(auditWeekNotes)
                } catch (e: InterruptedException) {
                    Timber.d("stopped thread")
                    return
                }
            }
        }
        return t
    }

}
