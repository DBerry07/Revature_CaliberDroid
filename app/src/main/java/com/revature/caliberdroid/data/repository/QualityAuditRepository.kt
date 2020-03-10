package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.ui.qualityaudit.trainees.TraineeWithNotesLiveData
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekLiveData

object QualityAuditRepository {

    fun getAuditWeekNotes(
        batch: Batch,
        liveData: MutableLiveData<ArrayList<WeekLiveData>>
    ) /*LiveData<ArrayList<WeekLiveData>>*/ {
        APIHandler.getAuditWeekNotes(liveData, batch)
    }

    fun getSkillCategories(liveData: MutableLiveData<List<SkillCategory>>, batch: Batch, weekNumber: Int) {
        APIHandler.getSkillCategories(liveData, batch, weekNumber)
    }

    fun getTraineesWithNotes(
        batch: Batch,
        weekNumber: Int
    ): MutableLiveData<List<TraineeWithNotesLiveData>> {
        val liveData = MutableLiveData<List<TraineeWithNotesLiveData>>()

        APIHandler.getTraineesWithNotes(liveData, batch, weekNumber)

        return liveData
    }
}