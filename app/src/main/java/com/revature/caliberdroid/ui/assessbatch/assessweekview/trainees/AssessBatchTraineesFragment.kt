package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentAssessBatchTraineesBinding
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekViewModel
import com.revature.caliberdroid.ui.assessbatch.assessweekview.overview.assessment.TraineeAssessmentsRecycleAdapter

class AssessBatchTraineesFragment : Fragment() {

    companion object {
        fun newInstance() =
            AssessBatchTraineesFragment()
    }

    private var _binding: FragmentAssessBatchTraineesBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var viewModel: AssessWeekViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssessBatchTraineesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AssessWeekViewModel::class.java)
//        viewModel.assessWeekNotes.value!!.weekNumber=9
//        viewModel.initWeekData()
//        viewModel.getWeekData().observe(this, Observer {
//
//        })
//        initRecyclerView()

    }

    fun initRecyclerView() {
        var mAdapter = AssessBatchTraineeRecyclerAdapter(this.context)
        var linearLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.recycleAssessBatchTrainees.layoutManager = linearLayoutManager
        binding.recycleAssessBatchTrainees.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
