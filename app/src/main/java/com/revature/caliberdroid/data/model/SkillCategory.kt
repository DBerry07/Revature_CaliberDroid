package com.revature.caliberdroid.data.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class SkillCategory(val id: Long, val categoryId: Long, val category: String) : SortedListAdapter.ViewModel {

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is SkillCategory) {
            val other = model as SkillCategory
            return other.categoryId == categoryId
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is SkillCategory) {
            val other = model as SkillCategory
            return categoryId == other.categoryId
        }
        return false
    }
}