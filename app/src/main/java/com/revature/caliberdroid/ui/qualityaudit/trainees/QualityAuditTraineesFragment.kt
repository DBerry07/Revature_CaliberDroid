package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentQualityAuditTraineesBinding
import com.revature.caliberdroid.databinding.ItemQualityAuditTraineeBinding
import com.revature.caliberdroid.ui.qualityaudit.StatusHandler
import timber.log.Timber

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
        binding.rvAudittraineesTraineeslist.adapter = QualityAuditTraineesAdapter(
            requireContext(),
            ALPHABETICAL_COMPARATOR_AUDIT_TRAINEES,
            viewModel
        )

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

    class TraineeStatusHandler(
        val context: Context,
        val binding: ItemQualityAuditTraineeBinding,
        val viewModel: QualityAuditTraineesViewModel
    ) : StatusHandler {
        override fun onStatusClick(view: View) {
            binding.includeItemaudittraineeStatusChooserLayout.root.visibility =
                when (binding.includeItemaudittraineeStatusChooserLayout.root.visibility) {
                    View.VISIBLE -> View.GONE
                    else -> View.VISIBLE
                }
        }

        override fun onStatusChoiceClick(view: View) {
            Timber.d("$view clicked")
            val previous = binding.traineeWithNotes!!.value!!.auditTraineeNotes!!.technicalStatus

            binding.traineeWithNotes!!.value!!.auditTraineeNotes!!.technicalStatus =
                when (view.id) {
                    R.id.img_chooserstatus_undefined -> "Undefined"
                    R.id.img_chooserstatus_poor -> "Poor"
                    R.id.img_chooserstatus_average -> "Average"
                    R.id.img_chooserstatus_good -> "Good"
                    else -> "Superstar"
                }
            if (previous != binding.traineeWithNotes!!.value!!.auditTraineeNotes!!.technicalStatus) {
                viewModel.putTraineeNotes(binding.traineeWithNotes!!.value!!)
            }
            binding.includeItemaudittraineeStatusChooserLayout.root.visibility = View.GONE
        }
    }
}
