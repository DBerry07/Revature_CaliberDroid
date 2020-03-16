package com.revature.caliberdroid.ui.batches

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.repository.BatchRepository
import com.revature.caliberdroid.databinding.FragmentCreateBatchBinding
import com.revature.caliberdroid.ui.locations.LocationsViewModel
import com.revature.caliberdroid.ui.trainers.TrainersViewModel
import com.revature.caliberdroid.util.DateConverter
import timber.log.Timber
import java.text.DateFormatSymbols
import java.util.*
import kotlin.collections.ArrayList


class CreateBatchFragment : Fragment() {

    // data binding
    private var _binding: FragmentCreateBatchBinding? = null
    private val binding
        get() = _binding!!
    private val args: CreateBatchFragmentArgs by navArgs()
    private var batch: Batch? = null

    // view models
    private val locationsViewModel: LocationsViewModel by activityViewModels()
    private val batchViewModel: BatchesViewModel by activityViewModels()
    private val trainerViewModel: TrainersViewModel by activityViewModels()

    private lateinit var trainingTypeAdapter: ArrayAdapter<CharSequence>
    private lateinit var locationAdapter: ArrayAdapter<String>
    private lateinit var trainerAdapter: ArrayAdapter<String>
    private var locationsFromAPI = ArrayList<String>()
    private var trainersFromAPI = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        activity?.window?.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        )

        // ViewModel Init
        locationsViewModel.getLocations()
        batchViewModel.getTrainers()
        trainerViewModel.getTrainers()

        // Inflate the layout for this fragment
        _binding = FragmentCreateBatchBinding.inflate(layoutInflater)

        batch = args.selectedBatch

        spinnerInit()
        datePickerInit()

        binding.btnCreateBatchCancel.setOnClickListener{
            findNavController().navigate(CreateBatchFragmentDirections.actionCreateBatchFragmentToManageBatchFragment())
        }

        // Set title for the create batch path
        if(batch == null) {
            binding.btnCreateBatchCreate.text = getString(R.string.btn_editBatch_update)
            (activity as AppCompatActivity).supportActionBar?.title = "Create Batch"
        }

        binding.btnCreateBatchCreate.setOnClickListener {
            if(checkEmptyFields()){
                if(batch!=null) {
                    updateBatch()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createBatch()
                    }
                }
            }
        }

        // if edit batch path fill with the existing values
        if(batch != null) {
            setExistingValues()
        }

        return binding.root
    }

    private fun getMonth(month: Int): String? {
        return DateFormatSymbols().months[month].substring(0, 3)
    }


    private fun checkEmptyFields(): Boolean {
        var result = true
        if(TextUtils.isEmpty(binding.etCreateBatchNameInput.text)) {
            binding.etCreateBatchNameInput.error = "Batch Name cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchSkillInput.text)) {
            binding.etCreateBatchSkillInput.error = "Skill focus cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchStartInput.text)) {
            binding.etCreateBatchStartInput.error = "Start date cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchEndInput.text)) {
            binding.etCreateBatchEndInput.error = "End date cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchGoodGradeInput.text)) {
            binding.etCreateBatchGoodGradeInput.error = "Good grade cannot be empty"
            result = false
        }
        if(TextUtils.isEmpty(binding.etCreateBatchPassingGradeInput.text)) {
            binding.etCreateBatchPassingGradeInput.error = "Passing grade cannot be empty"
            result = false
        }

        return result
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createBatch() {
        // creating an empty batch that will be filled with data from the edit text views
        batch = Batch(
            0,
            binding.etCreateBatchNameInput.text.toString(),
            binding.spinnerCreateBatchTrainingType.selectedItem.toString(),
            binding.etCreateBatchSkillInput.text.toString(),
            binding.spinnerCreatebatchTrainer.selectedItem.toString(),
            binding.spinnerCreatebatchCotrainer.selectedItem.toString(),
            0,
            binding.spinnerCreatebatchLocation.selectedItem.toString(),
            DateConverter.getTimestamp(binding.etCreateBatchStartInput.text.toString()),
            DateConverter.getTimestamp(binding.etCreateBatchEndInput.text.toString()),
            binding.etCreateBatchGoodGradeInput.text.toString().toInt(),
            binding.etCreateBatchPassingGradeInput.text.toString().toInt(),
            0
        )
        BatchRepository.addBatch(batch!!)
        Snackbar.make(view!!,"Batch Created Successfully!", Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(CreateBatchFragmentDirections.actionCreateBatchFragmentToManageBatchFragment())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateBatch() {
        setBatchValues()
        BatchRepository.editBatch(batch!!)
        Snackbar.make(view!!,"Batch Updated Successfully!", Snackbar.LENGTH_SHORT).show()
        findNavController().navigate(CreateBatchFragmentDirections.actionCreateBatchFragmentToManageBatchFragment())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setBatchValues() {
        batch?.trainingName = binding.etCreateBatchNameInput.text.toString()
        batch?.trainerName = binding.spinnerCreatebatchTrainer.selectedItem.toString()
        batch?.coTrainerName = binding.spinnerCreatebatchCotrainer.selectedItem.toString()
        batch?.location = binding.spinnerCreatebatchLocation.selectedItem.toString()
        batch?.skillType = binding.etCreateBatchSkillInput.text.toString()
        batch?._startDate = DateConverter.getTimestamp(binding.etCreateBatchStartInput.text.toString())
        batch?._endDate = DateConverter.getTimestamp(binding.etCreateBatchEndInput.text.toString())
        batch?.trainingType = binding.spinnerCreateBatchTrainingType.selectedItem.toString()
        batch?.goodGrade = binding.etCreateBatchGoodGradeInput.text.toString().toInt()
        batch?.passingGrade = binding.etCreateBatchPassingGradeInput.text.toString().toInt()
    }

    private fun spinnerInit() {

        // populate Training Type spinner
        trainingTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.training_type_array, R.layout.custom_spinner)
        trainingTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCreateBatchTrainingType.adapter = trainingTypeAdapter

        // populate Location Spinner
        locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { locations ->
            if(locations != null){
                for (location in locations) {
                    locationsFromAPI.add(location.getAddressLines())
                }
                this.locationAdapter = ArrayAdapter<String>(requireContext(), R.layout.custom_spinner, locationsFromAPI)
                this.locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCreatebatchLocation.adapter = locationAdapter

                val spinnerPosition = this.locationAdapter.getPosition(batch?.location)
                binding.spinnerCreatebatchLocation.setSelection(spinnerPosition)

            } else {
                Timber.d("locationsViewModel is null")
            }
        })

        // populate Trainer/Co-trainer Spinner
        trainerViewModel.trainersLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { trainers ->
            if(trainers != null){
                trainersFromAPI.add("none")
                for (trainer in trainers) {
                    trainersFromAPI.add(trainer.name)
                }
                this.trainerAdapter = ArrayAdapter<String>(requireContext(), R.layout.custom_spinner, trainersFromAPI)
                this.trainerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCreatebatchTrainer.adapter = trainerAdapter
                binding.spinnerCreatebatchCotrainer.adapter = trainerAdapter

                var spinnerPosition = this.trainerAdapter.getPosition(batch?.trainerName)
                binding.spinnerCreatebatchTrainer.setSelection(spinnerPosition)

                spinnerPosition = this.trainerAdapter.getPosition(batch?.coTrainerName)
                binding.spinnerCreatebatchCotrainer.setSelection(spinnerPosition)

            } else {
                Timber.d("locationsViewModel is null")
            }
        })

    }

    private fun datePickerInit() {
        val cal = Calendar.getInstance()
        val y = cal.get(Calendar.YEAR)
        val m = cal.get(Calendar.MONTH)
        val d = cal.get(Calendar.DAY_OF_MONTH)

        binding.etCreateBatchStartInput.setOnClickListener {
            val dpd = DatePickerDialog(
                context!!,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val dateString = "${getMonth(month)} $day, $year"
                    binding.etCreateBatchStartInput.text = dateString
                }, y, m, d
            )
            dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dpd.show()
        }

        binding.etCreateBatchEndInput.setOnClickListener {
            val dpd = DatePickerDialog(
                context!!,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    val dateString = "${getMonth(month)} $day, $year"
                    binding.etCreateBatchEndInput.text = dateString
                }, y, m, d
            )
            dpd.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dpd.show()
        }
    }

    private fun setExistingValues() {
        binding.run {
            etCreateBatchNameInput.setText(batch?.trainingName)
            etCreateBatchSkillInput.setText(batch?.skillType)
            etCreateBatchStartInput.text = batch?.startDate
            etCreateBatchEndInput.text = batch?.endDate
            etCreateBatchGoodGradeInput.setText(batch?.goodGrade.toString())
            etCreateBatchPassingGradeInput.setText(batch?.passingGrade.toString())
        }

        val spinnerPosition: Int = trainingTypeAdapter.getPosition(batch?.trainingType)
        binding.spinnerCreateBatchTrainingType.setSelection(spinnerPosition)

    }

}
