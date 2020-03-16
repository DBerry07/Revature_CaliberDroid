package com.revature.caliberdroid.ui.qualityaudit.overall

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.repository.QualityAuditRepository
import timber.log.Timber

class QualityAuditOverallViewModel : ViewModel() {

    val skillCategoryLiveData: MutableLiveData<ArrayList<SkillCategory>> = MutableLiveData(arrayListOf())
    var categories: MutableLiveData<ArrayList<Category>> = MutableLiveData(arrayListOf())
    lateinit var batch: Batch
    var weekNumber: Int = -1

    fun getActiveCategoryNames(): Array<String?> {
        val names = arrayOfNulls<String>(categories.value!!.size)
        for (i in categories.value!!.indices) {
            names[i] = categories.value!![i].skillCategory
        }
        return names
    }

    fun getCategoryBooleanArray(): BooleanArray {
        val booleans = BooleanArray(categories.value!!.size)
        for (i in categories.value!!.indices) {
            booleans[i] = false
            for (alreadySelected in skillCategoryLiveData.value!!) {
                if (categories.value!![i].categoryId == alreadySelected.categoryId) {
                    booleans[i] = true
                }
            }
        }
        return booleans
    }

    fun getSkillCategories(batch: Batch, weekNumber: Int) {
        QualityAuditRepository.getSkillCategories(skillCategoryLiveData, batch, weekNumber)
    }

    fun loadCategories() {
        QualityAuditRepository.getActiveCategories(categories)
    }

    fun updateAuditCategories(categories: ArrayList<Category>) {

        // look to see if new categories were added
        val toAdd: ArrayList<Category> = arrayListOf()

        for (category in categories) {
            // add category if there are no current skill categories for this audit
            // otherwise check if the skill category was previously selected
            if (skillCategoryLiveData.value!!.isEmpty()) {
                toAdd.add(category)
            } else {
                for (i in skillCategoryLiveData.value!!.indices) {
                    val alreadyAdded = skillCategoryLiveData.value!![i]
                    if (category.categoryId == alreadyAdded.categoryId) {
                        break
                    } else if (i == (skillCategoryLiveData.value!!.size - 1)) {
                        toAdd.add(category)
                    }
                }
            }
        }
        if (weekNumber > 0) {
            QualityAuditRepository.addAuditSkillCategories(toAdd, batch, weekNumber, skillCategoryLiveData)
        } else {
            Timber.d("Week number not set properly, cannot add category. Week number: %s", weekNumber)
        }

        // look to see if previously added categories were removed
        val toDelete: ArrayList<SkillCategory> = arrayListOf()

        for (skillCategory in skillCategoryLiveData.value!!) {

            // check if this is still an active category
            // if not it cannot be deleted this way
            var canBeDeleted = false

            for (category in this.categories.value!!) {
                if (skillCategory.categoryId == category.categoryId) {
                    canBeDeleted = true
                    break
                }
            }

            if (canBeDeleted) {
                // if no skill categories are checked, delete existing categories
                // otherwise check if skill category is still checked
                if (categories.isEmpty()) {
                    toDelete.add(skillCategory)
                } else {
                    for (i in categories.indices) {
                        val toBeAdded = categories[i]
                        if (skillCategory.categoryId == toBeAdded.categoryId) {
                            break
                        } else if (i == (categories.size - 1)) {
                            toDelete.add(skillCategory)
                        }
                    }
                }
            }
        }

        QualityAuditRepository.removeAuditSkillCategories(toDelete, skillCategoryLiveData)

    }

}
