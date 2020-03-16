package com.revature.caliberdroid.ui.qualityaudit.overall

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.data.repository.CategoryRepository
import com.revature.caliberdroid.data.repository.QualityAuditRepository

class QualityAuditOverallViewModel : ViewModel() {

    val skillCategoryLiveData: LiveData<List<SkillCategory>> = MutableLiveData()
    var categories: MutableLiveData<ArrayList<Category>> = MutableLiveData(arrayListOf())

    fun getSkillCategories(batch: Batch, weekNumber: Int) {
        QualityAuditRepository.getSkillCategories(skillCategoryLiveData as MutableLiveData, batch, weekNumber)
    }

    fun loadCategories(): MutableLiveData<ArrayList<Category>> {
        if (categories.value!!.size == 0) {
            QualityAuditRepository.getActiveCategories(categories)
        }
        return categories
    }

}
