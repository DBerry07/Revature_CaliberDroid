package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.GradeAPIHandler
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.ItemAssessmentTraineeGradesBinding
import com.revature.caliberdroid.databinding.ItemTraineeAssessmentBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import kotlinx.android.synthetic.main.item_assessment_trainee_grades.view.*

class TraineeAssessmentsRecycleAdapter(
    val assessWeekViewModel: AssessWeekViewModel,
    val assessment: Assessment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TraineeGradeViewHolder(
            ItemAssessmentTraineeGradesBinding.inflate(LayoutInflater.from(parent.context),parent,false), assessWeekViewModel
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val trainee = assessWeekViewModel.trainees!!.value!![position]
        var grade = getGradeForTrainee(trainee)

        (holder as TraineeGradeViewHolder).bind(trainee, grade)

    }

    override fun getItemCount(): Int {
        return assessWeekViewModel.trainees!!.value!!.size
    }

    class TraineeGradeViewHolder constructor( val binding: ItemAssessmentTraineeGradesBinding, val assessWeekViewModel: AssessWeekViewModel): RecyclerView.ViewHolder(binding.root){

        fun bind(trainee: Trainee, grade: Grade){
            binding.score = grade.score!!
            binding.traineeName = trainee.name

            //save grade on focus change
            binding.etAssessmentTraineeGradesRowGrade.onFocusChangeListener = View.OnFocusChangeListener{v, hasFocus ->
                if(!hasFocus){
                    grade.score = Integer(binding.etAssessmentTraineeGradesRowGrade.text.toString()).toInt()
                    GradeAPIHandler.putGrade(grade)
                }
            }
        }
    }

    fun getGradeForTrainee(trainee: Trainee): Grade {

        for(grade in assessWeekViewModel.assessWeekNotes.grades){
            if(grade.traineeId==trainee.traineeId){
                return grade
            }
        }

        return Grade(assessment.assessmentId,trainee.traineeId)
    }
}