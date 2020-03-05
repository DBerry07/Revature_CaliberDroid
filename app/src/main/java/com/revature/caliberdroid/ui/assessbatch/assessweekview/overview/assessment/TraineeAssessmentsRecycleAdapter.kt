package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import kotlinx.android.synthetic.main.item_trainee_assessment.view.*

class TraineeAssessmentsRecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var grades : List<Grade> = ArrayList()
    private var assessments: List<Assessment> = ArrayList()

    fun submitGradeList(gradeList: List<Grade>){
        grades = gradeList
    }

    fun submitAssessmentList(assessmentList: List<Assessment>){
        assessments = assessmentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AssessmentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_assessment_trainee_grades,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AssessmentViewHolder ->{
                holder.bind(grades.get(position),getAssessment(grades.get(position).assessmentId!!)!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return grades.size
    }



    fun getAssessment(assessmentId:Long):Assessment? {
        for(assessment in assessments){
            if(assessment.assessmentId==assessmentId){
                return assessment
            }
        }
        return null
    }

    class AssessmentViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val assessmentType: TextView = itemView.tv_trainee_assessment_item_label
        val assessmentGrade: TextView = itemView.tv_trainee_assessment_item_grade
        fun bind(grade: Grade, assessment: Assessment){
            assessmentType.setText(assessment.assessmentType+": ")
            assessmentGrade.setText(grade.score!!)
        }
    }


}