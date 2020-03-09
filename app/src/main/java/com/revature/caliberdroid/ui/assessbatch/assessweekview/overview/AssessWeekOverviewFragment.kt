package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.databinding.FragmentAssessWeekOverviewBinding
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekFragmentDirections
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel

class AssessWeekOverviewFragment : Fragment(), AssessmentsRecyclerAdapter.OnItemClickListener {

    companion object {
        fun newInstance() =
            AssessWeekOverviewFragment()
    }

    private val assessWeekViewModel: AssessWeekViewModel by activityViewModels()
    private var _assessWeekOverviewBinding: FragmentAssessWeekOverviewBinding? = null
    private val assessWeekOverviewBinding get() = _assessWeekOverviewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _assessWeekOverviewBinding = FragmentAssessWeekOverviewBinding.inflate(inflater)

        assessWeekOverviewBinding.assessWeekModel = assessWeekViewModel

        assessWeekOverviewBinding.root.setOnClickListener(View.OnClickListener {
            assessWeekOverviewBinding.etAssessweekBatchnotes.clearFocus()
        })

        assessWeekOverviewBinding.etAssessweekBatchnotes.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                if ((v as EditText).text.toString() != assessWeekViewModel.assessWeekNotes.batchNote.noteContent) {
                    var note = assessWeekViewModel.assessWeekNotes.batchNote.copy()
                    note.noteContent = (v as EditText).text.toString()
                    assessWeekViewModel.saveBatchNote(note)
                }
            }
        })

        assessWeekOverviewBinding.etAssessweekBatchnotes.addTextChangedListener {
            if (it.toString() != assessWeekViewModel.assessWeekNotes.batchNote.noteContent) {
                var note = assessWeekViewModel.assessWeekNotes.batchNote.copy()
                note.noteContent = it.toString()
                assessWeekViewModel.startDelayedSaveThread(note, assessWeekViewModel::saveBatchNote)
            }
        }

        assessWeekOverviewBinding.btnAssessweekAddassessment.setOnClickListener(View.OnClickListener {

            val builder = AlertDialog.Builder(it.context)

            builder.setTitle(resources.getString(R.string.dialog_create_assessment))
            builder.setPositiveButton(R.string.button_create, DialogInterface.OnClickListener { dialog, which ->

            })
            builder.setNegativeButton(R.string.button_cancel, null)

            builder.show()
        })

        assessWeekOverviewBinding.rvAssessweekAssessments.layoutManager = LinearLayoutManager(requireContext())
        assessWeekOverviewBinding.rvAssessweekAssessments.adapter = AssessmentsRecyclerAdapter(requireContext(), assessWeekViewModel, this)

        return assessWeekOverviewBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _assessWeekOverviewBinding = null
    }

    override fun onAssessmentClicked(assessment: Assessment) {
        findNavController().navigate(AssessWeekFragmentDirections.actionAssessWeekViewFragmentToAssessmentTraineeGradesFragment(assessment.assessmentId))
    }
}
