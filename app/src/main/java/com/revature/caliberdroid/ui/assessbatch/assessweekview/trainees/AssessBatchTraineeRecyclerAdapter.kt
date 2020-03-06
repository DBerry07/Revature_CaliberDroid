package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Note
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.ui.assessbatch.trainees.TraineeAssessmentsRecycleAdapter
import kotlinx.android.synthetic.main.item_assess_batch_trainee.view.*

class AssessBatchTraineeRecyclerAdapter(var context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
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
            ),context
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TraineeViewHolder -> {
                var trainee = trainees.get(position)
                holder.bind(trainee,getGradesForTrainee(trainee.traineeId),assessments)
            }
        }
    }

    override fun getItemCount(): Int {
        return trainees.size
    }

    fun getGradesForTrainee(traineeId:Long):List<Grade> {
        var traineeGrades:ArrayList<Grade> = ArrayList()
        for(grade in grades){
            if(grade.traineeId == traineeId){
                traineeGrades.add(grade)
            }
        }
        return traineeGrades
    }

    class TraineeViewHolder constructor(
        itemView: View,
        context: Context?
    ): RecyclerView.ViewHolder(itemView) {
        var traineeName = itemView.tv_assess_batch_trainees_name
        var gradeRecycler = itemView.recycle_assess_batch_trainees_assessments
        var context = context
        fun bind(trainee:Trainee,grades:List<Grade>,assessments:List<Assessment>) {
            traineeName.setText(trainee.name)
            var mAdapter = TraineeAssessmentsRecycleAdapter()
//            mAdapter.submitGradeList(grades)
//            mAdapter.submitAssessmentList(assessments)
            var linearLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this.context)
            gradeRecycler.layoutManager = linearLayoutManager
            gradeRecycler.adapter = mAdapter
        }

    }

}