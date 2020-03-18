package com.revature.caliberdroid.data.model

import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.BR
import com.revature.caliberdroid.ui.assessbatch.weekselection.AssessWeekLiveData

data class AssessWeekNotes(var weekNumber: Int,
                           var batch: Batch?
) : BaseObservable(), SortedListAdapter.ViewModel {

    var batchAverage: Float = calculateBatchAverage()
        set(value) {
            field = value
            notifyChange()
        }
    var batchNote: Note = Note(weekNumber,batch!!.batchID)
        set(value) {
            field = value
            notifyChange()
        }
    var assessments: List<Assessment> = arrayListOf()
        set(value) {
            field = value
            notifyChange()
        }
    var grades: ArrayList<Grade> = arrayListOf()
        set(value) {
            field = value
            notifyChange()
        }
    var traineeNotes: MutableLiveData<List<Note>> = MutableLiveData(arrayListOf())
        set(value) {
            field = value
            notifyChange()
        }

    constructor(weekNumber: Int, batch: Batch, batchAverage: Float, batchNote: Note, assessments: ArrayList<Assessment>, grades: ArrayList<Grade>) : this(weekNumber,batch) {
        this.batchAverage = batchAverage
        this.batchNote = batchNote
        this.assessments = assessments
        this.grades = grades
    }

    override fun <T : kotlin.Any?> isContentTheSameAs(model: T): kotlin.Boolean {
        if (model is AssessWeekNotes) {
            val other = model as AssessWeekNotes
            return weekNumber == other.weekNumber && batch == other.batch
        }
        return false
    }

    override fun <T : kotlin.Any?> isSameModelAs(model: T): kotlin.Boolean {
        if (model is AssessWeekNotes) {
            val other = model as AssessWeekNotes
            return weekNumber == other.weekNumber && batch == other.batch
        }
        return false
    }

    fun calculateBatchAverage(): Float {
        var totalGrades = 0.0
        var totalAssessmentRawScores = 0.0
        // the following code calculates the actual average of the grades,
        // rather than what the website does which is just averaging the assessment averages
//        var assessmentCounts = HashMap<Long,Int>()
//        if(grades != null) {
//            for (grade in grades) {
//                totalGrades += grade.score!!
//                if (assessmentCounts.containsKey(grade.assessmentId)) {
//                    assessmentCounts.set(
//                        grade.assessmentId!!,
//                        assessmentCounts.get(grade.assessmentId as Long)!! + 1
//                    )
//                } else {
//                    assessmentCounts.put(grade.assessmentId!!, 1)
//                }
//            }
//            for (assessment in assessments) {
//                if (assessment.rawScore != null && assessmentCounts.get(assessment.assessmentId)!=null) {
//                    totalAssessmentRawScores += (assessmentCounts.get(assessment.assessmentId)!! * assessment.rawScore!!)
//                }
//            }
//        }
//        var batchAvg = (totalGrades/totalAssessmentRawScores*100).round().toFloat()

        //average of the averages
        var count =0
        var totalAverage = 0.0
        if(assessments!=null) {
            for (assessment in assessments) {
                totalAverage += getAssessmentAverage(assessment)
                count++
            }
        }
        var batchAvg = (totalAverage/count).round().toFloat()
        batchAverage = batchAvg
        return if(batchAvg.isNaN()) 0.0f else batchAvg
    }

    fun getAssessmentAverage(assessment: Assessment): Double {
        var totalPoints = 0.0
        var totalPossible = 0.0
        for(grade in grades) {
            if(grade.assessmentId==assessment.assessmentId) {
                totalPoints += grade.score!!
                totalPossible += assessment.rawScore!!
            }
        }
        return if(((totalPoints/totalPossible)*100).round().isNaN()) 0.0 else ((totalPoints/totalPossible)*100).round()
    }

    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

}





