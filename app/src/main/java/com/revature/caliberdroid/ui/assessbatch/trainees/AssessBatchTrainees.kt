package com.revature.caliberdroid.ui.assessbatch.trainees

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.revature.caliberdroid.R

class AssessBatchTrainees : Fragment() {

    companion object {
        fun newInstance() =
            AssessBatchTrainees()
    }

    private lateinit var viewModel: AssessBatchTraineesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_assess_batch_trainees, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AssessBatchTraineesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
