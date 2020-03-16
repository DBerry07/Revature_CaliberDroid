package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.GradeAPIHandler
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentAssessmentTraineeGradesBinding
import com.revature.caliberdroid.databinding.ItemAssessmentTraineeGradesBinding
import com.revature.caliberdroid.databinding.ItemTraineeAssessmentBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import kotlinx.android.synthetic.main.item_assessment_trainee_grades.view.*
import timber.log.Timber

class TraineeAssessmentsRecycleAdapter(
    val assessWeekViewModel: AssessWeekViewModel,
    val assessmentFragmentBinding: FragmentAssessmentTraineeGradesBinding,
    val assessment: Assessment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TraineeGradeViewHolder(
            ItemAssessmentTraineeGradesBinding.inflate(LayoutInflater.from(parent.context),parent,false), assessWeekViewModel, assessment
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val trainee = assessWeekViewModel.trainees!!.value!![position]
        var grade = getGradeForTrainee(trainee)

        (holder as TraineeGradeViewHolder).bind(trainee, grade, assessmentFragmentBinding)

    }

    override fun getItemCount(): Int {
        return assessWeekViewModel.trainees!!.value!!.size
    }

    class TraineeGradeViewHolder constructor( val binding: ItemAssessmentTraineeGradesBinding, val assessWeekViewModel: AssessWeekViewModel, var assessment: Assessment): RecyclerView.ViewHolder(binding.root){

        fun bind(trainee: Trainee, grade: Grade,assessmentFragmentBinding: FragmentAssessmentTraineeGradesBinding){
            binding.score = grade.score!!
            binding.traineeName = trainee.name
            var oldScore = grade.score!!

            //save grade on focus change
            binding.etAssessmentTraineeGradesRowGrade.onFocusChangeListener = View.OnFocusChangeListener{v, hasFocus ->
                if(!hasFocus){
                    if(binding.etAssessmentTraineeGradesRowGrade.text.toString().matches("[0-9]+".toRegex()) &&
                        Integer.valueOf(binding.etAssessmentTraineeGradesRowGrade.text.toString()) <= assessment.rawScore!!) {
                        if(Integer.valueOf(binding.etAssessmentTraineeGradesRowGrade.text.toString()) != oldScore) {

                            grade.score = Integer.valueOf(binding.etAssessmentTraineeGradesRowGrade.text.toString())
                            Timber.d(grade.toString())
                            if (grade.gradeId < 1) {
                                assessWeekViewModel.assessWeekNotes.grades.add(grade)
                            }
                            GradeAPIHandler.putGrade(grade)
                            binding.score = Integer.valueOf(binding.etAssessmentTraineeGradesRowGrade.text.toString())
                            assessmentFragmentBinding.average =
                                assessWeekViewModel.getAssessmentAverage(assessment).toFloat()
                            Timber.d("putting grade")
                        }
                    } else {
                        binding.score = oldScore
                        grade.score = oldScore
                        Toast.makeText(binding.root.context,"Invalid Grade",Toast.LENGTH_SHORT)
                        Timber.d("invalid grade")
                    }
                } else {
                    if(!binding.etAssessmentTraineeGradesRowGrade.text.toString().matches("[0-9]+".toRegex())) {
                        oldScore = Integer.valueOf(binding.etAssessmentTraineeGradesRowGrade.text.toString())
                    }
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