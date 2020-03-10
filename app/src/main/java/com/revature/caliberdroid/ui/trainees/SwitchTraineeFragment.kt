package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentSwitchTraineeBinding
import com.revature.caliberdroid.util.DateConverter
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class SwitchTraineeFragment : Fragment() {
    private var _binding: FragmentSwitchTraineeBinding? = null
    private val binding get() = _binding!!
    private val args: SwitchTraineeFragmentArgs by navArgs()
    private val viewModel: TraineeViewModel by viewModels()
    private var allBatches = ArrayList<Batch>()
    private var batchesThatTraineeCanSwitchTo = ArrayList<Batch>()
    private var currentBatchOfTrainee: Batch? = null
    private lateinit var traineeToSwitch:Trainee

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        traineeToSwitch = args.traineeToSwitch
        Timber.d("Trainee to Switch: "+traineeToSwitch)
        _binding = FragmentSwitchTraineeBinding.inflate(layoutInflater, container, false)

        //binding.
        viewModel.getAllBatches()

        viewModel.allBatchesLiveData.observe(viewLifecycleOwner, Observer { batches->
            Timber.d("All batches: "+batches)
            allBatches = batches
            currentBatchOfTrainee = matchTraineeToBatch(traineeToSwitch, batches)
            batchesThatTraineeCanSwitchTo = getBatchesTraineeCanBeSwitchedTo( parseBatchYear(currentBatchOfTrainee),allBatches )

            Timber.d("Trainee "+traineeToSwitch.name+" belongs in batch: "+currentBatchOfTrainee)
            Timber.d("Batches that trainee can switch to must be in year: "+parseBatchYear(currentBatchOfTrainee))
            Timber.d("Batches that trainee can switch to are: "+batchesThatTraineeCanSwitchTo)

//            for(batch in batches){
//                Timber.d("Batch year: "+parseBatchYear(DateConverter.getDate(batch._startDate)))
//            }
        })
        return binding.root
    }

    private fun parseBatchYear(batch: Batch?): String{
        if(batch != null){
            val fullDate = DateConverter.getDate(batch._startDate)
            val partsOfDate: List<String> = fullDate.split(" ")
            return partsOfDate.get(2).trim()
        }
        return ""
    }

    private fun matchTraineeToBatch(trainee: Trainee, batches: ArrayList<Batch>): Batch?{
        val batchIdToMatch = trainee.batchId
        var matchedBatch: Batch? = null
        if(batches != null){
            for(batch in batches){
                if(batch.batchID == batchIdToMatch){
                    matchedBatch = batch
                    break;
                }
            }
        }
        return matchedBatch
    }

    private fun getBatchesTraineeCanBeSwitchedTo(batchYear: String, batches: ArrayList<Batch>):ArrayList<Batch>{
        val matchedBatches: ArrayList<Batch> = ArrayList()
        for(batch in batches){
            val yearOfBatch = parseBatchYear(batch)
            if(yearOfBatch.trim().equals(batchYear.trim())){
                matchedBatches.add(batch)
            }
        }
        return  matchedBatches
    }
}
