package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentAddTraineeBinding
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class AddTraineeFragment() : Fragment() {

    private var _binding: FragmentAddTraineeBinding? = null
    private val binding get() = _binding!!
    private val model: TraineeViewModel by viewModels()

    //Get the argument of current batch from TraineeFragment
    private val args: AddTraineeFragmentArgs by navArgs()
    private lateinit var currentBatch: Batch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        currentBatch = args.currentBatch

        binding.apply {
            TMBtnCreateTrainee.setOnClickListener{
                createTrainee()
                findNavController().navigateUp()
                //Shouldn't navigate back using the way you have below
                //This method will create problems when user presses the back button
                    //findNavController().navigate(AddTraineeFragmentDirections.actionAddTraineeFragmentToTraineeFragment( currentBatch ))
            }
        }

        return view
    }

    fun createTrainee(){
        if (formCheck() == false){
            return
        }
        var name : String = "${binding.traineeLastName.text.toString()}, ${binding.traineeFirstName.text.toString()}"
        var jsonObject = JSONObject()
        jsonObject.put("firstName", binding.traineeFirstName.text.toString())
        jsonObject.put("lastName", binding.traineeLastName.text.toString())
        jsonObject.put("email", binding.traineeEmail.text.toString())
        jsonObject.put("trainingStatus", "Signed")
        jsonObject.put("phoneNumber", binding.traineePhone.text.toString())
        jsonObject.put("skypeId", binding.traineeSkype.text.toString())
        jsonObject.put("profileUrl", binding.traineeProfile.text.toString())
        jsonObject.put("recruiterName", binding.traineeRecruiter.text.toString())
        jsonObject.put("college", binding.traineeCollege.text.toString())
        jsonObject.put("degree", binding.traineeDegree.text.toString())
        jsonObject.put("major", binding.traineeMajor.text.toString())
        jsonObject.put("techScreenerName", binding.traineeScreener.text.toString())
        jsonObject.put("projectCompletion", "")
        jsonObject.put("name", name)
        jsonObject.put("batchId", currentBatch.batchID)
        model.postTrainee(jsonObject)
    }

    fun phoneFormatting(){
        //TODO: write method that formats phone number into 111-222-1234 format and checks for proper length
    }

    fun formCheck() : Boolean{
        if (binding.traineeFirstName.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the First Name field.", Toast.LENGTH_LONG)
            return false
        }
        if (binding.traineeLastName.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the Last Name field.", Toast.LENGTH_LONG)
            return false
        }
        if (binding.traineeCollege.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the College/University field.", Toast.LENGTH_LONG)
            return false
        }
        if (binding.traineeDegree.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the Degree field.", Toast.LENGTH_LONG)
            return false
        }
        if (binding.traineeEmail.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the Email field.", Toast.LENGTH_LONG)
            return false
        }
        if (binding.traineeMajor.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the Major field.", Toast.LENGTH_LONG)
            return false
        }
        if (binding.traineePhone.text.toString().isEmpty()){
            Toast.makeText(context, "Please fill the Phone Number field.", Toast.LENGTH_LONG)
            return false
        }
        return true
    }
}
