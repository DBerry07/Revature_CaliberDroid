package com.revature.caliberdroid.ui.qualityaudit.trainees

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.revature.caliberdroid.R

class QualityAuditTraineesFragment : Fragment() {

    private lateinit var viewModel: QualityAuditTraineesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quality_audit_trainees, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QualityAuditTraineesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
