package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.databinding.DialogAddAssessmentBinding
import com.revature.caliberdroid.databinding.FragmentAssessWeekOverviewBinding
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekFragmentDirections
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment.SkillArrayAdapter
import com.revature.caliberdroid.util.KeyboardUtil
import timber.log.Timber

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
                KeyboardUtil.hideSoftKeyboard(requireContext(),v)
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

        assessWeekOverviewBinding.btnAssessweekAddassessment.setOnClickListener(View.OnClickListener { showCreateAssessmentDialog(it, inflater) })

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

    private fun showCreateAssessmentDialog(view: View, inflater: LayoutInflater) {

        val builder = AlertDialog.Builder(view.context)

        builder.setTitle(resources.getString(R.string.dialog_create_assessment))

        val dialogBinding = DialogAddAssessmentBinding.inflate(inflater)
        dialogBinding.spinnerCreateassessmentdialogType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.assessment_types))
        var skills = assessWeekViewModel.getSkills()
        skills.observe(viewLifecycleOwner, Observer {
            dialogBinding.spinnerCreateassessmentdialogSkill.adapter = SkillArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, skills.value!!)
        })
        builder.setView(dialogBinding.root)

        builder.setPositiveButton(R.string.button_create, DialogInterface.OnClickListener { dialog, which ->
            var assessment = Assessment()

            var type = dialogBinding.spinnerCreateassessmentdialogType.selectedItem as String
            var skill = dialogBinding.spinnerCreateassessmentdialogSkill.selectedItem as Category

            assessment.assessmentType = dialogBinding.spinnerCreateassessmentdialogType.selectedItem as String
            assessment.assessmentCategory = (dialogBinding.spinnerCreateassessmentdialogSkill.selectedItem as Category).categoryId
            assessment.rawScore = dialogBinding.etCreateassessmentdialogPoints.text.toString().toInt()

            assessWeekViewModel.createAssessmentForBatchWeek(assessment)
        })
        builder.setNegativeButton(R.string.button_cancel, null)

        builder.show()
    }

}
