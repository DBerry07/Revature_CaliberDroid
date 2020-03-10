package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.databinding.ItemAssessweekoverviewAssessmentBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel

class AssessmentsRecyclerAdapter(
    val context: Context,
    val assessWeekViewModel: AssessWeekViewModel,
    private val onItemClickListener: OnItemClickListener
    ) :
    RecyclerView.Adapter<AssessmentsRecyclerAdapter.AssessmentViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssessmentViewHolder {
        return AssessmentViewHolder(ItemAssessweekoverviewAssessmentBinding.inflate(LayoutInflater.from(context),parent,false), onItemClickListener)
    }

    override fun getItemCount(): Int = assessWeekViewModel.assessWeekNotes.assessments.size

    override fun onBindViewHolder(holder: AssessmentViewHolder, position: Int) {
        holder.binding.assessment = assessWeekViewModel.assessWeekNotes.assessments[position]

        holder.binding.average = 95f

        holder.binding.btnAsssessmentsrecyclerTraineegrades.setOnClickListener {
            onItemClickListener.onAssessmentClicked(assessWeekViewModel.assessWeekNotes.assessments[position])
        }
    }


    class AssessmentViewHolder(val binding: ItemAssessweekoverviewAssessmentBinding, val onItemClickListener: OnItemClickListener )
        : RecyclerView.ViewHolder(binding.root) {

    }

    interface OnItemClickListener {
        fun onAssessmentClicked(assessment: Assessment)
    }
}