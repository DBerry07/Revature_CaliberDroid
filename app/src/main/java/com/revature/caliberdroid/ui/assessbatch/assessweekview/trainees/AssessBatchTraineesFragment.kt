package com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentAssessBatchTraineesBinding
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekViewModel
import com.revature.caliberdroid.ui.assessbatch.assessweekview.trainees.TraineeAssessmentsRecycleAdapter

class AssessBatchTraineesFragment : Fragment() {

    private val assessWeekViewModel: AssessWeekViewModel by activityViewModels()

    companion object {
        fun newInstance() =
            AssessBatchTraineesFragment()
    }

    private var _binding: FragmentAssessBatchTraineesBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssessBatchTraineesBinding.inflate(layoutInflater)

        initRecyclerView()

        return binding.root
    }

    fun initRecyclerView() {
        var mAdapter = AssessBatchTraineeRecyclerAdapter(this.context,assessWeekViewModel)
        var linearLayoutManager:RecyclerView.LayoutManager = LinearLayoutManager(this.context)
        binding.recycleAssessBatchTrainees.layoutManager = linearLayoutManager
        binding.recycleAssessBatchTrainees.adapter = mAdapter
        assessWeekViewModel.trainees.observe(viewLifecycleOwner, Observer {
            (binding.recycleAssessBatchTrainees.adapter as AssessBatchTraineeRecyclerAdapter).notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
