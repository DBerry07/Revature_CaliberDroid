package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.BR
import kotlin.properties.Delegates

data class AuditWeekNotes(val weekNumber: Int) : BaseObservable(), SortedListAdapter.ViewModel, Parcelable {

    var noteId: Long = -1L

    @Bindable
    var overallStatus = "Undefined"
        set(value) {
            field = value
            notifyPropertyChanged(BR.overallStatus)
        }

    var overallNotes = ""
        set(value) {
            field = value
            notifyChange()
        }

    var batchId by Delegates.notNull<Long>()

    constructor(parcel: Parcel) : this(parcel.readInt())

    constructor(noteId: Long, weekNumber: Int, overallStatus: String, overallNotes: String) : this(
        weekNumber
    ) {
        this.noteId = noteId
        this.overallStatus = overallStatus
        this.overallNotes = overallNotes
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        if (model is AuditWeekNotes) {
            val other = model as AuditWeekNotes
            return overallStatus == other.overallStatus && overallNotes == other.overallNotes
        }
        return false
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        if (model is AuditWeekNotes) {
            val other = model as AuditWeekNotes
            return weekNumber == other.weekNumber && batchId == other.batchId
        }
        return false
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(weekNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuditWeekNotes> {
        override fun createFromParcel(parcel: Parcel): AuditWeekNotes {
            return AuditWeekNotes(parcel)
        }

        override fun newArray(size: Int): Array<AuditWeekNotes?> {
            return arrayOfNulls(size)
        }
    }

}

