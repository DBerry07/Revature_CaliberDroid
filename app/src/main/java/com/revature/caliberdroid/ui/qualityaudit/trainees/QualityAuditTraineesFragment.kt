package com.revature.caliberdroid.ui.qualityaudit.trainees

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
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
import java.util.*
import kotlin.Comparator

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

        setHasOptionsMenu(true)

        viewModel.getTraineesWithNotes(args.batchSelected, args.auditWeekNotesSelected.weekNumber)

        subscribeToViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_bar, menu)

        (menu.findItem(R.id.search_bar).actionView as SearchView).apply {

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(query: String): Boolean {
                    val filteredModelList: List<TraineeWithNotesLiveData> =
                        filter(viewModel.traineesWithNotesLiveData.value!!, query)
                    (binding.rvAudittraineesTraineeslist.adapter as QualityAuditTraineesAdapter).edit()
                        .replaceAll(filteredModelList)
                        .commit()
                    binding.rvAudittraineesTraineeslist.scrollToPosition(0)
                    return true
                }
            })

            queryHint = "Search by trainer's name"

            binding.root.setOnClickListener { this.clearFocus() }
        }
    }

    private fun subscribeToViewModel() {
        viewModel.traineesWithNotesLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvAudittraineesTraineeslist.adapter as QualityAuditTraineesAdapter).edit()
                .replaceAll(it)
                .commit()
            binding.tvAudittraineesEmptytrainees.visibility = when (it.size) {
                0 -> View.VISIBLE
                else -> View.GONE
            }
        })
    }

    private fun filter(
        models: List<TraineeWithNotesLiveData>,
        query: String
    ): List<TraineeWithNotesLiveData> {
        val lowerCaseQuery = query.toLowerCase(Locale.ROOT)
        val filteredModelList: MutableList<TraineeWithNotesLiveData> = ArrayList()
        var text: String
        for (model in models) {
            text = model.value!!.trainee!!.name!!.toLowerCase(Locale.ROOT)
            if (text.contains(lowerCaseQuery)) {
                filteredModelList.add(model)
            }
        }
        return filteredModelList
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
