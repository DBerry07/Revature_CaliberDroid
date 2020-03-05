package com.revature.caliberdroid.ui.assessbatch.assessweekview.overview

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentAssessWeekOverviewBinding
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekViewModel

class AssessWeekOverviewFragment : Fragment() {

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

        return assessWeekOverviewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _assessWeekOverviewBinding = null
    }
}
