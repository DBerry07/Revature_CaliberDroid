package com.revature.caliberdroid.ui.qualityaudit.weekselection

import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditWeekNotes

class WeekLiveData : MutableLiveData<AuditWeekNotes>(), SortedListAdapter.ViewModel {
    override fun <T> isSameModelAs(model: T): Boolean {
        if (model is WeekLiveData) {
            val other = model as WeekLiveData
            return value!!.isSameModelAs(other.value)
        }
        return false
    }

    override fun <T> isContentTheSameAs(model: T): Boolean {
        if (model is WeekLiveData) {
            val other = model as WeekLiveData
            return value!!.isContentTheSameAs(other.value)
        }
        return false
    }
}