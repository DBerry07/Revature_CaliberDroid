package com.revature.caliberdroid.data.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class SkillCategory(val ID: Long, val category: String) : SortedListAdapter.ViewModel {

    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is SkillCategory) {
            val other = model as SkillCategory
            return other.category == category
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is SkillCategory) {
            val other = model as SkillCategory
            return category == other.category
        }
        return false
    }
}