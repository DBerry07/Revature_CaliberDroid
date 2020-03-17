package com.revature.caliberdroid.ui.assessbatch.weekselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentWeekSelectionBinding
import com.revature.caliberdroid.ui.assessbatch.AssessWeekViewModel

class AssessWeekSelectionFragment : Fragment(), WeekSelectionAdapter.OnItemClickListener {

    private var _binding: FragmentWeekSelectionBinding? = null
    private val binding get() = _binding!!
    private val WEEK_NUMBER_COMPARATOR = Comparator<AssessWeekLiveData> { a,b -> a.value!!.weekNumber.compareTo(b.value!!.weekNumber)}
    private val args: AssessWeekSelectionFragmentArgs by navArgs()

    private val assessWeekViewModel: AssessWeekViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeekSelectionBinding.inflate(inflater)

        val batch = args.batchSelected

        binding.batch = batch

        binding.rvWeekselectionWeeks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeekselectionWeeks.adapter = WeekSelectionAdapter(requireContext(), WEEK_NUMBER_COMPARATOR, this)

        assessWeekViewModel.loadBatchWeeks(batch)
        assessWeekViewModel.batchAssessWeekNotes.observe(viewLifecycleOwner, Observer {
            (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter)
                .edit()
                .replaceAll(assessWeekViewModel.batchAssessWeekNotes.value!!)
                .commit()
        })

        binding.btnWeekselectionAddweek.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialog_add_week_title))
                .setMessage(resources.getString(R.string.dialog_add_week_message))
                .setPositiveButton(R.string.button_add_week) { dialog, which ->
                    assessWeekViewModel.addWeek()
                }
                .setNegativeButton(R.string.button_cancel, null)
                .show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProviders.of(this).get(AssessWeekSelectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onWeekClick(weekClicked: AssessWeekLiveData) {
        assessWeekViewModel.assessWeekNotes = weekClicked.value!!
        findNavController().navigate(AssessWeekSelectionFragmentDirections.actionAssessWeekSelectionFragmentToAssessWeekViewFragment())
    }

}
