package com.revature.caliberdroid.data.repository

import androidx.lifecycle.MutableLiveData
import com.revature.caliberdroid.data.api.APIHandler
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.model.TraineeWithNotes

object QualityAuditRepository {

    fun getSkillCategories(liveData: MutableLiveData<List<SkillCategory>>, batch: Batch, weekNumber: Int) {
        APIHandler.getSkillCategories(liveData, batch, weekNumber)
    }

    fun getTraineesWithNotes(liveData: MutableLiveData<List<TraineeWithNotes>>, batch: Batch, weekNumber: Int) {
        APIHandler.getTraineesWithNotes(liveData, batch, weekNumber)
    }
}