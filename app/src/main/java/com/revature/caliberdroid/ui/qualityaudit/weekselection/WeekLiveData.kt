package com.revature.caliberdroid.ui.qualityaudit.weekselection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditWeekNotes

class WeekLiveData : MutableLiveData<AuditWeekNotes>(), SortedListAdapter.ViewModel {
    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        return value!!.isContentTheSameAs((model as WeekLiveData).value)
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        return value!!.isSameModelAs((model as WeekLiveData).value)
    }
}