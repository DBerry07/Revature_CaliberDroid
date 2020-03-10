package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentAddTraineeBinding
import org.json.JSONObject

class EditTraineeFragment : Fragment() {

    private var _binding: FragmentAddTraineeBinding? = null
    private val binding get() = _binding!!
    private val model: TraineeViewModel by viewModels()

    //Get the arguments from navigation
    private val args: EditTraineeFragmentArgs by navArgs()
    private lateinit var trainee : Trainee
    private var batchID : Long? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        trainee = args.trainee
        batchID = args.batchId

        //Fill fields with trainee's predefined data
        binding.apply {
            binding.TMBtnCreateTrainee.setText("Submit Edits")
            binding.TMBtnCreateTrainee.setOnClickListener{
                updateTrainee(batchID!!)
                findNavController().navigateUp()
            }
            binding.traineeFirstName.setText(trainee.name.toString().split(",")[1].trim())
            binding.traineeLastName.setText(trainee.name.toString().split(",")[0].trim())
            binding.traineeCollege.setText(trainee.college)
            binding.traineeDegree.setText(trainee.degree)
            binding.traineeMajor.setText(trainee.major)
            binding.traineeEmail.setText(trainee.email)
            binding.traineePhone.setText(trainee.phoneNumber)
            binding.traineeProfile.setText(trainee.profileUrl)
            binding.traineeProject.setText(trainee.projectCompletion)
            binding.traineeRecruiter.setText(trainee.recruiterName)
            binding.traineeScreener.setText(trainee.techScreenerName)
            binding.traineeSkype.setText(trainee.skypeId)

            var i = 0
            while (i < binding.traineeStatus.adapter.count) {
                if (binding.traineeStatus.adapter.getItem(i).toString().equals(trainee.trainingStatus)) {
                        binding.traineeStatus.setSelection(i)
                    }
                i++
            }


        }

        return view
    }

    fun updateTrainee(batchID: Long){
        var jsonObject = JSONObject()
        var name = "${binding.traineeLastName.text}, ${binding.traineeFirstName.text}"

        jsonObject.put("traineeId", trainee.traineeId)
        jsonObject.put("resourceId", if (trainee.resourceId.equals("null")) { null } else { trainee.resourceId })
        jsonObject.put("name", name)
        jsonObject.put("email", binding.traineeEmail.text.toString())
        jsonObject.put("trainingStatus", binding.traineeStatus.selectedItem.toString())
        jsonObject.put("batchId", batchID)
        jsonObject.put("phoneNumber", binding.traineePhone.text.toString())
        jsonObject.put("skypeId", binding.traineeSkype.text.toString())
        jsonObject.put("profileUrl", binding.traineeProfile.text.toString())
        jsonObject.put("recruiterName", binding.traineeRecruiter.text.toString())
        jsonObject.put("college", binding.traineeCollege.text.toString())
        jsonObject.put("degree", binding.traineeDegree.text.toString())
        jsonObject.put("major", binding.traineeMajor.text.toString())
        jsonObject.put("techScreenerName", binding.traineeScreener.text.toString())
        jsonObject.put("techScreenScore", trainee.techScreenScore)
        jsonObject.put("projectCompletion",binding.traineeProject.text.toString())
        //The Add Trainee API call has no field for "flagStatus", and is initialized as null
        //However, passing "flagStatus" as "null" when editing a trainee results in an error, hence the if statement
        jsonObject.put("flagStatus", if (trainee.flagStatus.equals("null")) { "NONE" } else { trainee.flagStatus})
        jsonObject.put("flagNotes", trainee.flagNotes)
        jsonObject.put("flagAuthor", trainee.flagAuthor)
        jsonObject.put("flagTimestamp", trainee.flagTimestamp)
        jsonObject.put("firstName", binding.traineeFirstName.text.toString())
        jsonObject.put("lastName", binding.traineeLastName.text.toString())
        model.putTrainee(jsonObject)
    }
}