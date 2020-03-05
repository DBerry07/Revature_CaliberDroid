package com.revature.caliberdroid.ui.qualityaudit.weekselection

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

import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.databinding.FragmentWeekSelectionBinding
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekSelectionAdapter.OnItemClickListener

class QualityAuditWeekSelectionFragment : Fragment(), OnItemClickListener {

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_AUDIT_WEEK_NOTES: java.util.Comparator<AuditWeekNotes> =
            Comparator { a, b -> a.weekNumber.compareTo(b.weekNumber) }
    }

    private var _binding: FragmentWeekSelectionBinding? = null
    private val binding
        get() = _binding!!
    private val args: QualityAuditWeekSelectionFragmentArgs by navArgs()
    private val viewModel: QualityAuditWeekSelectionViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWeekSelectionBinding.inflate(inflater)

        binding.batch = args.batchSelected

        viewModel.batchSelected = args.batchSelected

        viewModel.getAuditWeekNotes()

        binding.rvWeekselectionWeeks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeekselectionWeeks.adapter = WeekSelectionAdapter(requireContext(), ALPHABETICAL_COMPARATOR_AUDIT_WEEK_NOTES, this)

        subscribeToViewModel()

        (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter).edit()
            .replaceAll(viewModel.auditWeekNotesLiveData.value!!.list)
            .commit()

        binding.setLifecycleOwner(viewLifecycleOwner)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onWeekClick(weekClicked: AuditWeekNotes) {
        findNavController().navigate(QualityAuditWeekSelectionFragmentDirections.actionAuditWeekSelectionFragmentToQualityAuditOverallFragment(args.batchSelected, weekClicked))
    }

    private fun subscribeToViewModel() {

        viewModel.auditWeekNotesLiveData.observe(viewLifecycleOwner, Observer { value ->
            if (value != null) {
//                (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter).edit()
//                    .replaceAll(value.list)
//                    .commit()
            binding.rvWeekselectionWeeks.adapter.let {
                if (it != null) {
                    value.applyChange(it)

                }
            }
            }
        })
    }
}
