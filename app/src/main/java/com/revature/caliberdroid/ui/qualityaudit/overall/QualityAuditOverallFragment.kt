package com.revature.caliberdroid.ui.qualityaudit.overall

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.databinding.FragmentQualityAuditOverallBinding

class QualityAuditOverallFragment : Fragment() {

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES: java.util.Comparator<SkillCategory> =
            Comparator { a, b -> a.category.compareTo(b.category) }
    }

    private var _binding: FragmentQualityAuditOverallBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: QualityAuditOverallViewModel by activityViewModels()
    private val args: QualityAuditOverallFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQualityAuditOverallBinding.inflate(inflater)

        binding.auditWeekNotes = args.auditWeekNotesSelected
        binding.batch = args.batchSelected

        viewModel.getSkillCategories(args.batchSelected, args.auditWeekNotesSelected.weekNumber)

        binding.rvAuditoverallCategories.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.rvAuditoverallCategories.adapter = SkillCategoryAdapter(requireContext(), ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES)

        binding.btnAuditoverallTrainees.setOnClickListener {
            findNavController().navigate(QualityAuditOverallFragmentDirections.actionQualityAuditOverallFragmentToQualityAuditTraineesFragment())
        }

        binding.btnAuditoverallSave.setOnClickListener {
            findNavController().navigate(QualityAuditOverallFragmentDirections.actionQualityAuditOverallFragmentToQualityAuditBatchSelectionFragment())
        }

        subscribeToViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeToViewModel() {
        viewModel.skillCategoryLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvAuditoverallCategories.adapter as SkillCategoryAdapter).edit()
                .replaceAll(it)
                .commit()
        })
    }
}
