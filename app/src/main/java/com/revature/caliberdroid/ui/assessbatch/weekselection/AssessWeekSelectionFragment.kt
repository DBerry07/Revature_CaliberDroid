package com.revature.caliberdroid.ui.assessbatch.weekselection

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.*
import com.revature.caliberdroid.databinding.FragmentWeekSelectionBinding
import com.revature.caliberdroid.ui.assessbatch.assessweekview.AssessWeekViewModel
import org.json.JSONException
import org.json.JSONObject

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

            (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter).edit().replaceAll(assessWeekViewModel.batchAssessWeekNotes.value!!).commit()

        })



        binding.btnWeekselectionAddweek.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getString(R.string.dialog_add_week_title))
                .setMessage(resources.getString(R.string.dialog_add_week_message))
                .setPositiveButton(R.string.button_add_week) { dialog, which -> }
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
