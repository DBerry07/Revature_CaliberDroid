package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Note
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.ui.assessbatch.trainees.AssessBatchTraineeRecyclerAdapter
import com.revature.caliberdroid.ui.assessbatch.trainees.TraineeAssessmentsRecycleAdapter
import kotlinx.android.synthetic.main.item_assess_batch_trainee.view.*

class AssessBatchTraineeRecyclerAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var grades : List<Grade> = ArrayList()
    private var assessments: List<Assessment> = ArrayList()
    private var trainees: List<Trainee> = ArrayList()
    private var notes: List<Note> = ArrayList()

    fun submitGradeList(gradeList: List<Grade>){ grades = gradeList }

    fun submitAssessmentList(assessmentList: List<Assessment>) {assessments = assessmentList}

    fun submitTraineeList(traineeList: List<Trainee>) {trainees = traineeList}

    fun submitNoteList(noteList: List<Note>) {notes = noteList}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TraineeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_assess_batch_trainee,
                parent,
                false
            ), grades,assessments
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TraineeViewHolder -> {
                holder.bind(trainees.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return trainees.size
    }

    class TraineeViewHolder constructor(
        itemView: View,grades:List<Grade>, assessments:List<Assessment>
    ): RecyclerView.ViewHolder(itemView) {
        val traineeName = itemView.tv_assess_batch_trainees_name
        val gradeRecycler = itemView.recycle_assess_batch_trainees_assessments
        fun bind(trainee:Trainee) {
            traineeName.setText(trainee.name)

        }

        fun initRecyclerView() {
            var mAdapter = TraineeAssessmentsRecycleAdapter()
        }
    }

}