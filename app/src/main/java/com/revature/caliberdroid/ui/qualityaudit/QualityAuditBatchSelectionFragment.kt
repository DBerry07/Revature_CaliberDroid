package com.revature.caliberdroid.ui.qualityaudit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.revature.caliberdroid.ui.batchselection.BatchSelectionAdapter.OnItemClickListener

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch

/**
 * A simple [Fragment] subclass.
 */
class QualityAuditBatchSelectionFragment : Fragment(), OnItemClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quality_audit_batch_selection, container, false)
    }

    override fun onBatchClick(batchClicked: Batch) {
        findNavController().navigate(QualityAuditBatchSelectionFragmentDirections.actionQualityAuditBatchSelectionFragmentToAuditWeekSelectionFragment(batchClicked))
    }

}
