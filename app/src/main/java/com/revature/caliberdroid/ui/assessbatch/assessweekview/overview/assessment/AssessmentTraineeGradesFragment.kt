package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.databinding.FragmentAssessmentTraineeGradesBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel

class AssessmentTraineeGradesFragment : Fragment() {

    companion object {
        fun newInstance() =
            AssessmentTraineeGradesFragment()
    }

    private val assessWeekViewModel: AssessWeekViewModel by activityViewModels()
    private var _assessmentTraineeGradesBinding: FragmentAssessmentTraineeGradesBinding? = null
    private val assessmentTraineeGradesBinding get() = _assessmentTraineeGradesBinding!!
    private val args: AssessmentTraineeGradesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _assessmentTraineeGradesBinding = FragmentAssessmentTraineeGradesBinding.inflate(inflater)

        val assessment = getAssessment(args.assessmentId)

        assessmentTraineeGradesBinding.assessment = assessment
        assessmentTraineeGradesBinding.average = assessmentAverage(assessment)

        assessmentTraineeGradesBinding.rvAssessmentTraineegrades.layoutManager = LinearLayoutManager(requireContext())
        assessmentTraineeGradesBinding.rvAssessmentTraineegrades.adapter = TraineeAssessmentsRecycleAdapter(assessWeekViewModel,assessmentTraineeGradesBinding, assessment)

        return assessmentTraineeGradesBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun getAssessment(assessmentId: Long): Assessment {
        for (assessment in assessWeekViewModel.assessWeekNotes.assessments) {
            if (assessment.assessmentId == assessmentId) {
                return assessment
            }
        }
        return Assessment(-1)
    }

    fun assessmentAverage(assessment: Assessment) : Float {

        var sum = 0
        var count = 0

        for (grade in assessWeekViewModel.assessWeekNotes.grades) {
            if (grade.assessmentId == assessment.assessmentId) {
                sum += grade.score!!
                count++
            }
        }

        return if (count == 0) 0f else (sum.toFloat()/count)/assessment.rawScore!! * 100
    }

}
