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
import kotlinx.android.synthetic.main.item_trainee_assessment.view.*

class TraineeAssessmentsRecycleAdapter(var grades: List<Grade>, var assessments: List<Assessment>,val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items : List<Grade> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AssessmentViewHolder(
            ItemTraineeAssessmentBinding.inflate(LayoutInflater.from(context))
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is AssessmentViewHolder ->{
                holder.bind(items.get(position),getAssessmentForGrade(items.get(position).assessmentId!!)!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getAssessmentForGrade(assessmentId:Long) : Assessment? {
        for(assessment in assessments) {
            if(assessment.assessmentId == assessmentId) {
                return assessment
            }
        }
        return null
    }

    class AssessmentViewHolder constructor(
        val binding: ItemTraineeAssessmentBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(grade: Grade, assessment: Assessment){
            binding.grade=grade
            binding.assessment = assessment
        }
    }
}