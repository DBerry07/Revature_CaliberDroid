package com.revature.caliberdroid.ui.qualityaudit.weekselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentWeekSelectionBinding
import com.revature.caliberdroid.ui.qualityaudit.weekselection.WeekSelectionAdapter.OnItemClickListener

class QualityAuditWeekSelectionFragment : Fragment(), OnItemClickListener {

    companion object {
        @JvmField
        val ALPHABETICAL_COMPARATOR_AUDIT_WEEK_NOTES: java.util.Comparator<WeekLiveData> =
            Comparator { a, b -> a.value!!.weekNumber.compareTo(b.value!!.weekNumber) }
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

        binding.lifecycleOwner = viewLifecycleOwner

        binding

        binding.btnWeekselectionAddweek.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialog_add_week_title))
                .setMessage(resources.getString(R.string.dialog_add_week_message))
                .setPositiveButton(R.string.button_add_week) { dialog, which ->
                    viewModel.addWeek()
                }
                .setNegativeButton(R.string.button_cancel, null)
                .show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title =
            "${viewModel.batchSelected.trainerName} - ${viewModel.batchSelected.skillType}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvWeekselectionWeeks.adapter = null
        _binding = null
    }

    override fun onWeekClick(weekClicked: WeekLiveData) {
        findNavController().navigate(
            QualityAuditWeekSelectionFragmentDirections.actionAuditWeekSelectionFragmentToQualityAuditOverallFragment(
                args.batchSelected,
                weekClicked.value!!
            )
        )
    }

    private fun subscribeToViewModel() {
        viewModel.auditWeekNotesLiveData.observe(viewLifecycleOwner, Observer { value ->
            binding.tvWeekselectionNoresults.visibility = if (value.size == 0) View.VISIBLE else View.GONE
            (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter).edit()
                .replaceAll(value)
                .commit()
        })
    }
}
