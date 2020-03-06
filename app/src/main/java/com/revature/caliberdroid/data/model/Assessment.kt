package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter

data class Assessment(
    val assessmentId:Long,
    var rawScore:Int? = 0,
    var assessmentTitle:String? = "",
    var assessmentType:String? = "",
    var weekNumber:Int? = 0,
    var batchId:Long? = 0,
    var assessmentCategory:Int? = 0
): SortedListAdapter.ViewModel, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun <T : Any?> isContentTheSameAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> isSameModelAs(model: T): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(assessmentId)
        parcel.writeValue(rawScore)
        parcel.writeString(assessmentTitle)
        parcel.writeString(assessmentType)
        parcel.writeValue(weekNumber)
        parcel.writeValue(batchId)
        parcel.writeValue(assessmentCategory)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Assessment> {
        override fun createFromParcel(parcel: Parcel): Assessment {
            return Assessment(parcel)
        }

        override fun newArray(size: Int): Array<Assessment?> {
            return arrayOfNulls(size)
        }
    }
}