package com.revature.caliberdroid.ui.trainees

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs

import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.DialogTraineeSwitchBinding
import com.revature.caliberdroid.databinding.FragmentSwitchTraineeBinding
import com.revature.caliberdroid.util.DateConverter
import timber.log.Timber
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

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

        viewModel.getAllBatches()

        viewModel.allBatchesLiveData.observe(viewLifecycleOwner, Observer { _batches->
            init(_batches)
        })

        viewModel.traineeBeingSwitchedLiveData.observe(viewLifecycleOwner, Observer { _trainee ->
            traineeToSwitch = _trainee
            init(allBatches)
        })


        return binding.root
    }

    private fun init(_batches: ArrayList<Batch>){
        Timber.d("All batches: "+_batches)
        allBatches = _batches
        currentBatchOfTrainee = matchTraineeToBatch(traineeToSwitch, _batches)
        batchesThatTraineeCanSwitchTo = getBatchesTraineeCanBeSwitchedTo( currentBatchOfTrainee ,allBatches )


        binding.tvBatchesAvailableHeader.text = "Batches this Year ("+batchesThatTraineeCanSwitchTo.size+" options available)"
        binding.TMSwitchTvName.text = traineeToSwitch.name
        binding.TMSwitchTvYear.text = parseBatchYear(currentBatchOfTrainee)
        binding.TMSwitchTvBatch.text = currentBatchOfTrainee?.trainingName


        binding.rvBatches.adapter = SwitchTraineeAdapter(batchesThatTraineeCanSwitchTo, SwitchTraineeOnClick())
        Timber.d("Trainee "+traineeToSwitch.name+" belongs in batch: "+currentBatchOfTrainee)
        Timber.d("Batches that trainee can switch to must be in year: "+parseBatchYear(currentBatchOfTrainee))
        Timber.d("Batches that trainee can switch to are: "+batchesThatTraineeCanSwitchTo)
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

    private fun getBatchesTraineeCanBeSwitchedTo(currentBatch: Batch?, batches: ArrayList<Batch>):ArrayList<Batch>{
        val matchedBatches: ArrayList<Batch> = ArrayList()
        if( currentBatch != null){
            val yearOfCurrentBatch: String = parseBatchYear(currentBatch)
            for(batch in batches){
                val yearOfBatch = parseBatchYear(batch)
                if(yearOfBatch.trim().equals(yearOfCurrentBatch.trim())){
                    if(batch.batchID != currentBatch.batchID){
                        matchedBatches.add(batch)
                    }
                }
            }
        }
        sortBatches(matchedBatches)
        return  matchedBatches
    }

    private fun sortBatches(batches: ArrayList<Batch>):ArrayList<Batch>{
        Collections.sort(batches, object: Comparator<Batch>{
            override fun compare(o1: Batch?, o2: Batch?): Int {
                return if( o1 != null && o2 != null ){
                    o1.trainingName.compareTo(o2.trainingName)
                }else{
                    0
                }
            }
        })
        return batches
    }

    interface SwitchTraineeOnClickInterface{
        fun onSwitchTrainee(batch: Batch)
    }

    inner class SwitchTraineeOnClick: SwitchTraineeOnClickInterface{
        override fun onSwitchTrainee(batch: Batch) {
            Timber.d("Confirm that trainee should be switched to batch: "+batch)
            var builder: AlertDialog.Builder = AlertDialog.Builder(context);
            val switchTraineeBinding = DialogTraineeSwitchBinding.inflate(
                LayoutInflater.from(context), view!! as ViewGroup, false
            )
            val switchTraineeDialogView = switchTraineeBinding.root
            switchTraineeBinding.batch = batch
            switchTraineeBinding.tvStartDate.text = "Start Date: "+DateConverter.getDate(batch._startDate)
            switchTraineeBinding.tvEndDate.text = "End Date: "+DateConverter.getDate(batch._endDate)

            builder.setView(switchTraineeDialogView)
                .setPositiveButton(R.string.btn_confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.switchTrainee(traineeToSwitch,batch)
                    }
                )
                .setNegativeButton(R.string.btn_cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                    }
                )
            var alertDialog: AlertDialog = builder.create();
            alertDialog.show()
        }

    }
}
