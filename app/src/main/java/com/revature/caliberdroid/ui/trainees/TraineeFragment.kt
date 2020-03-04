package com.revature.caliberdroid.ui.trainees

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.revature.caliberdroid.R
import com.revature.revaturetraineemanagment.TraineeAdapter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * A simple [Fragment] subclass.
 */
class TraineeFragment : Fragment() {

    var traineeData : ArrayList<HashMap<String, String>> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view : View = inflater.inflate(R.layout.fragment_trainee, container, false)
        createData()
        Log.d("traineeData", traineeData.toString())
        val traineeLayoutManager = LinearLayoutManager(view.context)
        val traineeAdapter = TraineeAdapter(traineeData)
        val recyclerView = view.findViewById<RecyclerView>(R.id.TM_recycler)

        (recyclerView?.itemAnimator as SimpleItemAnimator?)!!.supportsChangeAnimations = false
        recyclerView.layoutManager = traineeLayoutManager
        recyclerView.adapter = traineeAdapter
        recyclerView.setHasFixedSize(false);

        val button : Button = view.findViewById(R.id.MB_btn_goto_add_trainee)

        button.setOnClickListener {
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.action_traineeFragment_to_addTraineeFragment)
        }

        // Inflate the layout for this fragment
        return view
    }

    fun createData(){
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
