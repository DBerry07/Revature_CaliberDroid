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
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.databinding.FragmentAddTraineeBinding
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 */
class AddTraineeFragment() : Fragment() {

    private var _binding: FragmentAddTraineeBinding? = null
    private val binding get() = _binding!!
    private var batchId : Long? = null
    private val model: TraineeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddTraineeBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        //TODO: Add way to get batch ID from previous trainee list fragment
        batchId = 50

        binding.apply {
            TMBtnCreateTrainee.setOnClickListener{
                createTrainee()
                    findNavController().navigate(R.id.action_addTraineeFragment_to_traineeFragment)
            }
        }

        return view
    }

    fun createTrainee(){
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
        jsonObject.put("batchId", batchId)
        model.postTrainee(jsonObject)
    }

    fun phoneFormatting(){
        //TODO: write method that formats phone number into 111-222-1234 format and checks for proper length
    }
}
