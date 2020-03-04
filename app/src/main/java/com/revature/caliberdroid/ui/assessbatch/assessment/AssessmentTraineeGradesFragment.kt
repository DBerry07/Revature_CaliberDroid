package com.revature.caliberdroid.ui.assessbatch.assessment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.revature.caliberdroid.R

class AssessmentTraineeGradesFragment : Fragment() {

    companion object {
        fun newInstance() =
            AssessmentTraineeGradesFragment()
    }

    private lateinit var viewModel: AssessmentTraineeGradesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_assessment_trainee_grades, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AssessmentTraineeGradesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
