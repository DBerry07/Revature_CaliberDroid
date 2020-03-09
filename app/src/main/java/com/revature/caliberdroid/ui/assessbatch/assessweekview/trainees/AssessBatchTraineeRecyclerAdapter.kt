package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Note
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.ItemAssessBatchTraineeBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel

class AssessBatchTraineeRecyclerAdapter(var context: Context?,var assessWeekViewModel: AssessWeekViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TraineeViewHolder(
            ItemAssessBatchTraineeBinding.inflate(LayoutInflater.from(context)), context!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TraineeViewHolder -> {
                var trainee = assessWeekViewModel.trainees.value!![position]
                holder.bind(trainee,
                    getNoteForTrainee(trainee.traineeId),
                    getGradesForTrainee(trainee.traineeId),
                    assessWeekViewModel.assessWeekNotes.assessments)
            }
        }
    }

    override fun getItemCount(): Int {
        if(assessWeekViewModel.trainees.value == null){
            return 0
        }
        return assessWeekViewModel.trainees.value!!.size
    }

    fun getGradesForTrainee(traineeId:Long):List<Grade> {
        var traineeGrades:ArrayList<Grade> = ArrayList()
        for(grade in assessWeekViewModel.assessWeekNotes.grades){
            if(grade.traineeId == traineeId){
                traineeGrades.add(grade)
            }
        }
        return traineeGrades
    }

    fun getNoteForTrainee(traineeId: Long):Note {
        var traineeNote = Note()
        for(note in assessWeekViewModel.assessWeekNotes.traineeNotes.value!!) {
            if(note.traineeId == traineeId) {
                return note
            }
        }
        return traineeNote
    }

    class TraineeViewHolder constructor(
        val binding: ItemAssessBatchTraineeBinding, val context: Context
    ): RecyclerView.ViewHolder(binding.root) {


        fun bind(trainee:Trainee,note:Note,grades:List<Grade>,assessments:List<Assessment>) {
            var mAdapter = TraineeAssessmentsRecycleAdapter(grades, assessments,context)
            var linearLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this.context)
            binding.recycleAssessBatchTraineesAssessments.layoutManager = linearLayoutManager
            binding.recycleAssessBatchTraineesAssessments.adapter = mAdapter
            binding.trainee = trainee
            binding.traineeNote = note
        }

    }

}