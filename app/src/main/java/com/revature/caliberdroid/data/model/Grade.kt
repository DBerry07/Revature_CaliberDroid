package com.revature.caliberdroid.data.model

import android.os.Parcel
import android.os.Parcelable

data class Grade(
    val gradeId: Long,
    var dateReceived: String? = "",
    var score: Int? = 0,
    var assessmentId: Long? = 0,
    var traineeId: Long? = 0) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(gradeId)
        parcel.writeString(dateReceived)
        parcel.writeValue(score)
        parcel.writeValue(assessmentId)
        parcel.writeValue(traineeId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Grade> {
        override fun createFromParcel(parcel: Parcel): Grade {
            return Grade(parcel)
        }

        override fun newArray(size: Int): Array<Grade?> {
            return arrayOfNulls(size)
        }
    }

}