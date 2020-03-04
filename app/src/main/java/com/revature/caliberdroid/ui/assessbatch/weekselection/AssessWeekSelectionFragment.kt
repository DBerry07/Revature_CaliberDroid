package com.revature.caliberdroid.ui.assessbatch.weekselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.databinding.FragmentWeekSelectionBinding

class AssessWeekSelectionFragment : Fragment(), WeekSelectionAdapter.OnItemClickListener {

    private val assessWeekSelectionViewModel: AssessWeekSelectionViewModel by activityViewModels()
    private var _binding: FragmentWeekSelectionBinding? = null
    private val binding get() = _binding!!
    private val WEEK_NUMBER_COMPARATOR = Comparator<AssessWeekNotes> { a,b -> a.weekNumber.compareTo(b.weekNumber)}
    private val args: AssessWeekSelectionFragmentArgs by navArgs()

    companion object {
        fun newInstance() =
            AssessWeekSelectionFragment()
    }

    private lateinit var viewModel: AssessWeekSelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeekSelectionBinding.inflate(inflater)

        binding.batch = args.batchSelected

        binding.rvWeekselectionWeeks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeekselectionWeeks.adapter = WeekSelectionAdapter(requireContext(), WEEK_NUMBER_COMPARATOR, this)

        val week1 = AssessWeekNotes(1, 93.2f, "They did so super duper awesome really good and great")
        val week2 = AssessWeekNotes(2, 88.8f, "not quite as good as last week, let's hope they don't totally blow it moving forward")

        (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter).edit().replaceAll(arrayListOf(week1,week2)).commit()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AssessWeekSelectionViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onWeekClick(weekClicked: AssessWeekNotes) {
        findNavController().navigate(AssessWeekSelectionFragmentDirections.actionAssessWeekSelectionFragmentToAssessWeekViewFragment(weekClicked))
    }

}
