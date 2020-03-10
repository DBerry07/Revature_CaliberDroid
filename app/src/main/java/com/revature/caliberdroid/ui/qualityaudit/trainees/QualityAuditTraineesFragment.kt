package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.databinding.FragmentQualityAuditTraineesBinding

class QualityAuditTraineesFragment : Fragment() {

    companion object {
        @JvmField
        val ALPHABETICAL_COMPARATOR_AUDIT_TRAINEES: java.util.Comparator<TraineeWithNotesLiveData> =
            Comparator { a, b -> a.value!!.trainee!!.name!!.compareTo(b.value!!.trainee!!.name!!) }
    }

    private val viewModel: QualityAuditTraineesViewModel by activityViewModels()
    private var _binding: FragmentQualityAuditTraineesBinding? = null
    private val binding get() = _binding!!
    private val args: QualityAuditTraineesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentQualityAuditTraineesBinding.inflate(layoutInflater)

        binding.rvAudittraineesTraineeslist.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAudittraineesTraineeslist.adapter = QualityAuditTraineesAdapter(requireContext(), ALPHABETICAL_COMPARATOR_AUDIT_TRAINEES)

        viewModel.getTraineesWithNotes(args.batchSelected, args.auditWeekNotesSelected.weekNumber)

        subscribeToViewModel()

        return binding.root
    }

    private fun subscribeToViewModel() {
        viewModel.traineesWithNotesLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvAudittraineesTraineeslist.adapter as QualityAuditTraineesAdapter).edit()
                .replaceAll(it)
                .commit()
        })
    }

}
