package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.PointerIcon
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.databinding.DialogAddAssessmentBinding
import com.revature.caliberdroid.databinding.FragmentAssessWeekOverviewBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekFragmentDirections
import com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment.SkillArrayAdapter
import com.revature.caliberdroid.util.KeyboardUtil
import timber.log.Timber
import java.text.ParseException

class AssessWeekOverviewFragment : Fragment(), AssessmentsRecyclerAdapter.OnItemClickListener, AssessmentsRecyclerAdapter.OnEditClickListener {

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
        assessWeekOverviewBinding.rvAssessweekAssessments.adapter = AssessmentsRecyclerAdapter(requireContext(), assessWeekViewModel, this, this)

        return assessWeekOverviewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "" + assessWeekViewModel.batch?.trainerName!!.substringBefore( " ") + " - Week " + assessWeekViewModel.assessWeekNotes.weekNumber
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _assessWeekOverviewBinding = null
    }

    override fun onAssessmentClicked(assessment: Assessment) {
        findNavController().navigate(AssessWeekFragmentDirections.actionAssessWeekViewFragmentToAssessmentTraineeGradesFragment(assessment.assessmentId))
    }

    private fun showCreateAssessmentDialog(view: View, inflater: LayoutInflater) {

        val builder = MaterialAlertDialogBuilder(view.context)

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

            assessment.assessmentType = dialogBinding.spinnerCreateassessmentdialogType.selectedItem as String
            assessment.assessmentCategory = (dialogBinding.spinnerCreateassessmentdialogSkill.selectedItem as Category).categoryId
            assessment.rawScore = dialogBinding.etCreateassessmentdialogPoints.text.toString().toInt()

            assessWeekViewModel.createAssessmentForBatchWeek(assessment)
        })
        builder.setNegativeButton(R.string.button_cancel, null)

        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false

        val assessmentPointValidator: TextWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    try {
                        var points = s.toString().toInt()
                        if (points <= 0 || points > 1000) badValue() else goodValue()
                    } catch (e: ParseException) {
                        badValue()
                    }
                } else {
                    badValue()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            fun badValue() {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                dialogBinding.tvCreateassessmentdialogPointsvalidationmessage.visibility = View.VISIBLE
            }

            fun goodValue() {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                dialogBinding.tvCreateassessmentdialogPointsvalidationmessage.visibility = View.GONE
            }

        }

        dialogBinding.etCreateassessmentdialogPoints.addTextChangedListener(assessmentPointValidator)

    }

    fun showEditAssessmentDialog(assessment: Assessment, view: View, inflater: LayoutInflater) {
        val builder = MaterialAlertDialogBuilder(view.context)

        builder.setTitle(resources.getString(R.string.dialog_create_assessment))

        val dialogBinding = DialogAddAssessmentBinding.inflate(inflater)

        dialogBinding.spinnerCreateassessmentdialogType.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.assessment_types))
        dialogBinding.spinnerCreateassessmentdialogType.setSelection(resources.getStringArray(R.array.assessment_types).indexOf(assessment.assessmentType))

        var skills = assessWeekViewModel.getSkills()
        skills.observe(viewLifecycleOwner, Observer {
            dialogBinding.spinnerCreateassessmentdialogSkill.adapter = SkillArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, skills.value!!)
            val category = assessWeekViewModel.findCategoryById(assessment.assessmentCategory!!)
            if (category.active) dialogBinding.spinnerCreateassessmentdialogSkill.setSelection(it.indexOf(category))
        })

        dialogBinding.etCreateassessmentdialogPoints.setText(assessment.rawScore.toString())

        builder.setView(dialogBinding.root)

        builder.setPositiveButton(R.string.btn_confirm, DialogInterface.OnClickListener { dialog, which ->

            var changed = false

            if (assessment.assessmentType != dialogBinding.spinnerCreateassessmentdialogType.selectedItem as String) {
                assessment.assessmentType = dialogBinding.spinnerCreateassessmentdialogType.selectedItem as String
                changed = true
            }

            if (assessment.assessmentCategory != (dialogBinding.spinnerCreateassessmentdialogSkill.selectedItem as Category).categoryId) {
                assessment.assessmentCategory = (dialogBinding.spinnerCreateassessmentdialogSkill.selectedItem as Category).categoryId
                changed = true
            }

            if (assessment.rawScore != dialogBinding.etCreateassessmentdialogPoints.text.toString().toInt()) {
                assessment.rawScore = dialogBinding.etCreateassessmentdialogPoints.text.toString().toInt()
                changed = true
            }

//            if (changed) assessWeekViewModel.updateAssessmentForBatchWeek(assessment)

        })

        builder.setNegativeButton(R.string.button_cancel, null)

        builder.setNeutralButton(R.string.button_delete, DialogInterface.OnClickListener { dialog, which ->
            assessWeekViewModel.deleteAssessment(assessment)
            assessWeekOverviewBinding.rvAssessweekAssessments.adapter!!.notifyDataSetChanged()
        })

        val dialog = builder.create()
        dialog.show()

        val assessmentPointValidator: TextWatcher = object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    try {
                        var points = s.toString().toInt()
                        if (points <= 0 || points > 1000) badValue() else goodValue()

                    } catch (e: ParseException) {
                        badValue()
                    }
                } else {
                    badValue()
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            fun badValue() {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = false
                dialogBinding.tvCreateassessmentdialogPointsvalidationmessage.visibility = View.VISIBLE
            }

            fun goodValue() {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).isEnabled = true
                dialogBinding.tvCreateassessmentdialogPointsvalidationmessage.visibility = View.GONE
            }

        }

        dialogBinding.etCreateassessmentdialogPoints.addTextChangedListener(assessmentPointValidator)
    }

    override fun onEditClicked(assessment: Assessment, view: View, inflater: LayoutInflater) {
        showEditAssessmentDialog(assessment, view, inflater)
    }

}
