package com.revature.caliberdroid.ui.trainees

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var _binding: FragmentTraineeBinding? = null
    private val binding get() = _binding!!

    //Get the argument of select batch from BatchesInfoFragment
    private val args: TraineeFragmentArgs by navArgs()
    private lateinit var currentBatch: Batch
    private var batchId : Long? = null

    private val model: TraineeViewModel by viewModels()

    //Moved declaration here because I need access to it outside of observe function
    private lateinit var traineeAdapter: TraineeAdapter
    private lateinit var myTrainees: ArrayList<Trainee>
    private lateinit var recyclerView : RecyclerView
    private lateinit var traineeLayoutManager : LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Set the selected batch in order to make API call for trainees
        currentBatch = args.BatchSelected
        Timber.d("Selected batch: $currentBatch")

        _binding = FragmentTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        batchId = currentBatch.batchID
        model.getTrainees(batchId!!)
        traineeLayoutManager = LinearLayoutManager(view.context)
        recyclerView = view.TM_recycler

        recyclerView.layoutManager = traineeLayoutManager

        //Disable the fade-in/fade-out
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerView.setHasFixedSize(false);

        hideKeyboard()
        updateTrainees()

        val button : Button = view.MB_btn_goto_add_trainee

        button.setOnClickListener {
            val navController = Navigation.findNavController(view)
            //Pass currently selected branch to add trainee
            navController.navigate(TraineeFragmentDirections.actionTraineeFragmentToAddTraineeFragment(currentBatch))
        }

        binding.swtActiveTrainees.setOnCheckedChangeListener { buttonView, isChecked ->
            Timber.d("State of switch: $isChecked")
            if(isChecked){
                val activeTraineesList: ArrayList<Trainee> = ArrayList()
                var currentTrainee: Trainee
                for( i in 0 until myTrainees.size){
                    currentTrainee = myTrainees.get(i)
                    if( currentTrainee.trainingStatus?.toLowerCase().equals("dropped")){
                        Timber.d("This trainee is dropped/inactive: $currentTrainee")
                        continue
                    }
                    activeTraineesList.add(currentTrainee)
                }
                traineeAdapter.trainees = activeTraineesList
                traineeAdapter.notifyDataSetChanged()
            }else{
                traineeAdapter.trainees = myTrainees
                traineeAdapter.notifyDataSetChanged()
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideKeyboard(){
        val input = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        input.hideSoftInputFromWindow(activity!!.currentFocus?.windowToken, 0)
    }

    fun updateTrainees(){
        model.traineesLiveData.observe(viewLifecycleOwner, Observer<List<Trainee>>{ trainees ->

            //Sorts trainees by (last) name
            myTrainees = ArrayList(trainees.sortedBy { trainee: Trainee -> trainee.name!!.toUpperCase() })

            traineeAdapter = TraineeAdapter(myTrainees, batchId!!, this)
            recyclerView.layoutManager = traineeLayoutManager
            recyclerView.adapter = traineeAdapter
        })
    }

    override fun onResume() {
        super.onResume()
        updateTrainees()
    }

}
