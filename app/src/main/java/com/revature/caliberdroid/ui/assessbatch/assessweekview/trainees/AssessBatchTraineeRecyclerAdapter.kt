package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import android.content.Context
import android.opengl.Visibility
import android.text.Editable
import android.text.TextWatcher
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
import com.revature.caliberdroid.data.repository.AssessWeekRepository
import com.revature.caliberdroid.databinding.ItemAssessBatchTraineeBinding
import kotlinx.android.synthetic.main.item_assess_batch_trainee.view.*
import timber.log.Timber
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import com.revature.caliberdroid.util.KeyboardUtil

class AssessBatchTraineeRecyclerAdapter(var context: Context?,var assessWeekViewModel: AssessWeekViewModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TraineeViewHolder(
            ItemAssessBatchTraineeBinding.inflate(LayoutInflater.from(context),parent,false),assessWeekViewModel, context!!)
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
        if(traineeNote.weekNumber==-1) {
            traineeNote = Note(-1L,"","TRAINEE",assessWeekViewModel.assessWeekNotes.weekNumber,assessWeekViewModel.assessWeekNotes.batch!!.batchID,traineeId)
        }
        return traineeNote
    }

    class TraineeViewHolder constructor(
        val binding: ItemAssessBatchTraineeBinding, val assessWeekViewModel: AssessWeekViewModel, val context: Context
    ): RecyclerView.ViewHolder(binding.root) {


        fun bind(trainee:Trainee,note:Note,grades:List<Grade>,assessments:List<Assessment>) {
            var mAdapter = TraineeAssessmentsRecycleAdapter(grades, assessments,trainee.traineeId,context)
            var linearLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this.context)
            binding.recycleAssessBatchTraineesAssessments.layoutManager = linearLayoutManager
            binding.recycleAssessBatchTraineesAssessments.adapter = mAdapter
            binding.trainee = trainee
            binding.traineeNote = note
            var oldText = binding.etAssessBatchTraineesNote.text.toString()
            binding.etAssessBatchTraineesNote.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if(!hasFocus && !oldText.equals(v.et_assess_batch_trainees_note.text.toString())){
                    Timber.d("putting note"+(binding.traineeNote as Note).toString())
                    assessWeekViewModel.saveTraineeNote(binding.traineeNote as Note)
                } else {
                    oldText = v.et_assess_batch_trainees_note.text.toString()
                }
            }
            binding.etAssessBatchTraineesNote.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {}

                override fun beforeTextChanged(s: CharSequence, start: Int,
                                               count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence, start: Int,
                                           before: Int, count: Int) {
                    (binding.traineeNote as Note).noteContent = binding.etAssessBatchTraineesNote.text.toString()
                    assessWeekViewModel.startDelayedSaveThread(binding.traineeNote as Note, assessWeekViewModel::saveTraineeNote)
                }
            })

            binding.root.isFocusable = true
            binding.root.isFocusableInTouchMode = true
            binding.root.setOnClickListener {
                Timber.d("clicking on away")
                KeyboardUtil.hideSoftKeyboard(context,itemView)
                it.requestFocus()
            }

            if(trainee.flagStatus.equals("RED")){
                binding.imgAssessBatchTraineeFlag.setImageResource(R.drawable.ic_red_flag)
            } else if(trainee.flagStatus.equals("GREEN")){
                binding.imgAssessBatchTraineeFlag.setImageResource(R.drawable.ic_green_flag)
            } else {
                binding.imgAssessBatchTraineeFlag.visibility = View.GONE
            }

            }
        }

    }

