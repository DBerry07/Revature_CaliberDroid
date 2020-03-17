package com.revature.caliberdroid.data.model

import androidx.databinding.BaseObservable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class Category(
    val categoryId:Long,
    var skillCategory: String,
    var active: Boolean
): SortedListAdapter.ViewModel {

    override fun toString(): String {
        return "Category(category=$categoryId, skillCategory='$skillCategory', active=$active)"
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        if (model is Category) {
            val other = model as Category
            return other.categoryId == categoryId
        }
        return false
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        if (model is Category) {
            val other = model as Category
            return other.categoryId == categoryId
        }
        return false
    }
}
