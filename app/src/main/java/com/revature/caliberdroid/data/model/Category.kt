package com.revature.caliberdroid.data.model

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class Category(
    val categoryId:Long,
    var skillCategory: String,
    var active: Boolean
): SortedListAdapter.ViewModel{

    override fun toString(): String {
        return "Category(category=$categoryId, skillCategory='$skillCategory', active=$active)"
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
