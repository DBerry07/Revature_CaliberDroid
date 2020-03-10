package com.revature.caliberdroid.ui.assessbatch.weekselection

import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AssessWeekNotes

class AssessWeekLiveData : MutableLiveData<AssessWeekNotes>() , SortedListAdapter.ViewModel {
    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is AssessWeekLiveData) {
            val other = model as AssessWeekLiveData
            return value!!.isSameModelAs(other.value)
        }
        return false
    }
    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is AssessWeekLiveData) {
            val other = model as AssessWeekLiveData
            return value!!.isContentTheSameAs(other.value)
        }
        return false
    }
}