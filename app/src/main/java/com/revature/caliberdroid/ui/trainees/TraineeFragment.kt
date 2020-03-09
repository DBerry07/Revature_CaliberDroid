package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentTraineeBinding
import com.revature.revaturetraineemanagment.TraineeAdapter
import kotlinx.android.synthetic.main.fragment_trainee.view.*
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class TraineeFragment : Fragment() {

    var traineeData : ArrayList<HashMap<String, String>> = ArrayList()

    private var _binding: FragmentTraineeBinding? = null
    private val binding get() = _binding!!

    //Get the argument of select batch from BatchesInfoFragment
    private val args: TraineeFragmentArgs by navArgs()
    private lateinit var currentBatch: Batch

    private val model: TraineeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Set the selected batch in order to make API call for trainees
        currentBatch = args.BatchSelected
        Timber.d("Selected batch: $currentBatch")

        _binding = FragmentTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        var batchId : Long = currentBatch.batchID
        model.getTrainees(batchId)
        var traineeLayoutManager = LinearLayoutManager(view.context)
        var recyclerView = view.TM_recycler

        recyclerView.layoutManager = traineeLayoutManager

        //Disable the fade-in/fade-out
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.setHasFixedSize(false);



        model.traineesLiveData.observe(viewLifecycleOwner, Observer<List<Trainee>>{ trainees ->

            //Sorts trainees by (last) name
            var myTrainees = trainees.sortedBy { trainee: Trainee -> trainee.name }

            var traineeAdapter = TraineeAdapter(myTrainees)
            recyclerView.layoutManager = traineeLayoutManager
            recyclerView.adapter = traineeAdapter
        })

        val button : Button = view.MB_btn_goto_add_trainee

        button.setOnClickListener {
            val navController = Navigation.findNavController(view)
            //Pass currently selected branch to add trainee
            navController.navigate(TraineeFragmentDirections.actionTraineeFragmentToAddTraineeFragment(currentBatch))
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
