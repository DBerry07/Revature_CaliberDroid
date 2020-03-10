package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.AuditTraineeWithNotes
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.SkillCategory
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
        liveData: MutableLiveData<List<AuditTraineeWithNotes>>,
        batch: Batch,
        weekNumber: Int
    ) {
        APIHandler.getTraineesWithNotes(liveData, batch, weekNumber)
    }
}