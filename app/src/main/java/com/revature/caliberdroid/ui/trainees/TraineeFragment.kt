package com.revature.caliberdroid.ui.trainees

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.api.TraineeAPIHandler
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.repository.TraineeRepository.getTrainees
import com.revature.caliberdroid.databinding.FragmentTraineeBinding
import com.revature.revaturetraineemanagment.TraineeAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_trainee.view.*

/**
 * A simple [Fragment] subclass.
 */
class TraineeFragment : Fragment() {

    var traineeData : ArrayList<HashMap<String, String>> = ArrayList()

    private var _binding: FragmentTraineeBinding? = null
    private val binding get() = _binding!!

    private val model: TraineeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        var batchId : Long = 50
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
            navController.navigate(R.id.action_traineeFragment_to_addTraineeFragment)
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
