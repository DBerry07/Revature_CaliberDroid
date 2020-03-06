package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class AuditWeekNotes(val weekNumber: Int) : BaseObservable(), SortedListAdapter.ViewModel, Parcelable {

    @Bindable var overallStatus = ObservableField<String?>()
        set(value) {
            field = value
            notifyChange()
        }
    @Bindable var overallNotes =  ObservableField<String?>()
        set(value) {
            field = value
            notifyChange()
        }

    constructor(parcel: Parcel) : this(parcel.readInt())

    constructor(weekNumber: Int, overallStatus: String?, overallNotes: String?) : this(weekNumber) {
        this.overallStatus.set(overallStatus)
        this.overallNotes.set(overallNotes)
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        if (model is AuditWeekNotes) {
            val other = model as AuditWeekNotes
            return overallStatus.get() == other.overallStatus.get() && overallNotes.get() == other.overallNotes.get()
        }
        return false
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        if (model is AuditWeekNotes) {
            val other = model as AuditWeekNotes
            return other.weekNumber == weekNumber
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

