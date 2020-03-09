package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import kotlinx.android.synthetic.main.item_assessment_trainee_grades.view.*

class TraineeAssessmentsRecycleAdapter(
    val assessWeekViewModel: AssessWeekViewModel,
    val assessment: Assessment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TraineeGradeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_assessment_trainee_grades,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val trainee = assessWeekViewModel.trainees!!.value!![position]
        val grade = getGradeForTrainee(trainee)

        (holder as TraineeGradeViewHolder).bind(trainee, grade.score!!)

    }

    override fun getItemCount(): Int {
        return assessWeekViewModel.trainees!!.value!!.size
    }

    class TraineeGradeViewHolder constructor( itemView: View): RecyclerView.ViewHolder(itemView){

        val traineeName: TextView = itemView.tv_assessment_trainee_grades_row_trainee_name
        val assessmentGrade: TextView = itemView.et_assessment_trainee_grades_row_grade

        fun bind(trainee: Trainee, grade: Int){
            traineeName.text = trainee.name + ":"
            assessmentGrade.text = grade.toString()
        }
    }

    fun getGradeForTrainee(trainee: Trainee): Grade {

        for(grade in assessWeekViewModel.assessWeekNotes.grades){
            if(grade.traineeId==trainee.traineeId){
                return grade
            }
        }

        return Grade(-1)
    }
}