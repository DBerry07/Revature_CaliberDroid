package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.databinding.ItemTraineeAssessmentBinding
import com.revature.caliberdroid.util.KeyboardUtil
import kotlinx.android.synthetic.main.item_trainee_assessment.view.*
import timber.log.Timber

class TraineeAssessmentsRecycleAdapter(var grades: List<Grade>, var assessments: List<Assessment>,var traineeId: Long,val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AssessmentViewHolder(
            ItemTraineeAssessmentBinding.inflate(LayoutInflater.from(context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AssessmentViewHolder ->{
                    holder.bind(
                        getGradeForAssessment(assessments.get(position).assessmentId,traineeId),
                        assessments.get(position)
                    )
            }
        }
    }

    override fun getItemCount(): Int {
        return assessments.size
    }

    fun getAssessmentForGrade(assessmentId:Long) : Assessment? {
        for(assessment in assessments) {
            if(assessment.assessmentId == assessmentId) {
                return assessment
            }
        }
        return null
    }

    fun getGradeForAssessment(assessmentId: Long, traineeId: Long): Grade {
        for(grade in grades) {
            if(grade.assessmentId==assessmentId && grade.traineeId==traineeId){
                return grade
            }
        }
        return Grade(-1,"",0,assessmentId,traineeId)
    }

    class AssessmentViewHolder constructor(
        val binding: ItemTraineeAssessmentBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(grade: Grade, assessment: Assessment){
            binding.grade=grade
            binding.assessment = assessment

            binding.root.isFocusableInTouchMode = true
            binding.root.isFocusable = true
            binding.root.setOnClickListener {
                Timber.d("clicking on away")
                KeyboardUtil.hideSoftKeyboard(binding.root.context,it)
                it.requestFocus()
            }
        }
    }
}