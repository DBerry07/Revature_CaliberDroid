package com.revature.caliberdroid.ui.assessbatch.assessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Grade
import kotlinx.android.synthetic.main.item_trainee_assessment.view.*

class TraineeAssessmentsRecycleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items : List<Grade> = ArrayList()

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
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(gradeList: List<Grade>){
        items = gradeList
    }

    class AssessmentViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val assessmentType: TextView = itemView.tv_trainee_assessment_item_label
        val assessmentGrade: TextView = itemView.tv_trainee_assessment_item_grade
        fun bind(grade: Grade){
            //assessment: Assessment = getAssessment(grade.assessmentId)
            //assessmentType.setText(assessment.type+": ")
            assessmentGrade.setText(grade.score!!)
        }
    }
}