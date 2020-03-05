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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.AssessWeekNotes
import com.revature.caliberdroid.data.model.Assessment
import com.revature.caliberdroid.data.model.Grade
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentWeekSelectionBinding
import org.json.JSONException
import org.json.JSONObject

class AssessWeekSelectionFragment : Fragment(), WeekSelectionAdapter.OnItemClickListener {

    private val assessWeekSelectionViewModel: AssessWeekSelectionViewModel by activityViewModels()
    private var _binding: FragmentWeekSelectionBinding? = null
    private val binding get() = _binding!!
    private val WEEK_NUMBER_COMPARATOR = Comparator<AssessWeekNotes> { a,b -> a.weekNumber.compareTo(b.weekNumber)}
    private val args: AssessWeekSelectionFragmentArgs by navArgs()

    private lateinit var viewModel: AssessWeekSelectionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWeekSelectionBinding.inflate(inflater)

        val batch = args.batchSelected

        binding.batch = batch

        binding.rvWeekselectionWeeks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvWeekselectionWeeks.adapter = WeekSelectionAdapter(requireContext(), WEEK_NUMBER_COMPARATOR, this)

        val assessments = arrayListOf(
            Assessment(1,50,"good title","project",1, 5, 1),
            Assessment(2,75,"another good title","exam", 1, 5, 1),
            Assessment(3,25,"yet another good title","verbal", 1, 5, 1)
        )

        val assessments2 = arrayListOf<Assessment>()

        var grades = arrayListOf(
            Grade(1, "",50,1,1),
            Grade(2, "",45,1,2),
            Grade(3, "",47,1,3),
            Grade(4, "",70,2,1),
            Grade(5, "",72,2,2),
            Grade(6, "",75,2,3),
            Grade(7, "",15,3,1),
            Grade(8, "",24,3,2),
            Grade(9, "",18,3,3)
        )

//        val week1 = AssessWeekNotes(1, 93.2f, "They did so super duper awesome really good and great", batch, assessments, grades)
//        val week2 = AssessWeekNotes(2, 88.8f, "not quite as good as last week, let's hope they don't totally blow it moving forward", batch, arrayListOf(),
//            arrayListOf())

//        (binding.rvWeekselectionWeeks.adapter as WeekSelectionAdapter).edit().replaceAll(arrayListOf(week1,week2)).commit()

        binding.btnWeekselectionAddweek.setOnClickListener(View.OnClickListener {

            val builder = AlertDialog.Builder(it.context)

            builder.setTitle(resources.getString(R.string.dialog_add_week_title))
            builder.setMessage(resources.getString(R.string.dialog_add_week_message))
            builder.setPositiveButton(R.string.button_add_week, DialogInterface.OnClickListener { dialog, which ->

            })
            builder.setNegativeButton(R.string.button_cancel, null)

            builder.show()
        })

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
