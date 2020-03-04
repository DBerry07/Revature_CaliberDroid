package com.revature.caliberdroid.ui.qualityaudit.overall

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentQualityAuditOverallBinding

class QualityAuditOverallFragment : Fragment() {

    private var _binding: FragmentQualityAuditOverallBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var viewModel: QualityAuditOverallViewModel
    private val args: QualityAuditOverallFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQualityAuditOverallBinding.inflate(inflater)

        binding.auditWeekNotes = args.auditWeekNotesSelected
        binding.batch = args.batchSelected

        binding.btnAuditoverallTrainees.setOnClickListener {
            findNavController().navigate(QualityAuditOverallFragmentDirections.actionQualityAuditOverallFragmentToQualityAuditTraineesFragment())
        }

        binding.btnAuditoverallSave.setOnClickListener {
            findNavController().navigate(QualityAuditOverallFragmentDirections.actionQualityAuditOverallFragmentToQualityAuditBatchSelectionFragment())
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(QualityAuditOverallViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
