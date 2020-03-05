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
import com.revature.caliberdroid.data.model.Trainee
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

    private val viewModel: TraineeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        val model: TraineeViewModel by viewModels()

        createData()
        Log.d("traineeData", traineeData.toString())

        var traineeLayoutManager = LinearLayoutManager(view.context)
        var traineeAdapter = TraineeAdapter(traineeData)
        var recyclerView = view.TM_recycler

        recyclerView.layoutManager = traineeLayoutManager
        recyclerView.adapter = traineeAdapter

        (recyclerView.itemAnimator as SimpleItemAnimator)!!.supportsChangeAnimations = false
        recyclerView.setHasFixedSize(false);

        model.getTrainees()?.observe(viewLifecycleOwner, Observer<List<Trainee>>{ trainees ->
            //traineeData = trainees
            traineeAdapter = TraineeAdapter(traineeData)

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

    private fun createData(){
        var i = 0
        while (i < 10){
            var list : HashMap<String, String> = HashMap()
            list.put("name", "trainee #$i")
            list.put("email", "email $i")
            list.put("status", "status #$i")
            list.put("phone", "phone #$i")
            list.put("skype", "skype #$i")
            list.put("profile", "profile #$i")
            list.put("college", "college #$i")
            list.put("major", "major #$i")
            list.put("recruiter", "recruiter #$i")
            list.put("screener", "screener #$i")
            list.put("project", "project #$i")
            traineeData.add(list)
            i++
        }
    }

}
