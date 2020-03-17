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
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
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
                if (updateTrainee(batchID!!)) {
                    findNavController().navigateUp()
                }
            }
            binding.traineeFirstName.setText(trainee.name.toString().split(",")[1].trim())
            binding.traineeLastName.setText(trainee.name.toString().split(",")[0].trim())
            binding.traineeCollege.setText(trainee.college)
            binding.traineeDegree.setText(trainee.degree)
            binding.traineeMajor.setText(trainee.major)
            binding.traineeEmail.setText(trainee.email)
            binding.traineePhone.setText(trainee.phoneNumber)

            if (!trainee.profileUrl.equals("null") && trainee.profileUrl != null) {
                binding.traineeProfile.setText(trainee.profileUrl)
            }
            if (!trainee.projectCompletion.equals("null") && trainee.projectCompletion != null) {
                binding.traineeProject.setText(trainee.projectCompletion)
            }
            if (!trainee.recruiterName.equals("null") && trainee.recruiterName != null) {
                binding.traineeRecruiter.setText(trainee.recruiterName)
            }
            if (!trainee.techScreenerName.equals("null") && trainee.techScreenerName != null) {
                binding.traineeScreener.setText(trainee.techScreenerName)
            }
            if (!trainee.skypeId.equals("null") && trainee.skypeId != null) {
                binding.traineeSkype.setText(trainee.skypeId)
            }

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

    fun updateTrainee(batchID: Long) : Boolean{
        if (!formCheck()){
            return false
        }
        var jsonObject = JSONObject()
        var name = "${binding.traineeLastName.text}, ${binding.traineeFirstName.text}"

        jsonObject.put("traineeId", trainee.traineeId)
        jsonObject.put("resourceId", if (trainee.resourceId.equals("null")) { null } else { trainee.resourceId })
        jsonObject.put("name", name)
        jsonObject.put("email", binding.traineeEmail.text.toString())
        //jsonObject.put("trainingStatus", binding.traineeStatus.selectedItem.toString())
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
        Snackbar.make(binding.root, "Trainee edited successfully!", Snackbar.LENGTH_LONG).show()
        return true
    }

    fun formCheck() : Boolean{

        //First Name Checking
        if (binding.traineeFirstName.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the First Name field.", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (!checkName(binding.traineeFirstName.text.toString())){
            Snackbar.make(view!!, "Please enter a valid first name.", Snackbar.LENGTH_LONG).show()
            return false
        }
        //Last Name Checking
        if (binding.traineeLastName.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the Last Name field.", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (!checkName(binding.traineeLastName.text.toString())){
            Snackbar.make(view!!, "Please enter a valid last name.", Snackbar.LENGTH_LONG).show()
            return false
        }

        if (!checkAuto(binding.traineeStatus.text.toString())){
            Snackbar.make(view!!, "Please enter a valid trainee status.", Snackbar.LENGTH_LONG).show()
            return false
        }

        //Email Checking
        if (binding.traineeEmail.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the Email field.", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (!checkEmail(binding.traineeEmail.text.toString())){
            Snackbar.make(view!!, "Please enter a valid Email.", Snackbar.LENGTH_LONG).show()
            return false
        }

        //Phone number checking
        if (binding.traineePhone.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the Phone Number field.", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (!checkPhone(binding.traineePhone.text.toString())){
            Snackbar.make(view!!, "Please enter a phone number with format 123-456-7890.", Snackbar.LENGTH_LONG).show()
            return false
        }

        //Empty field checking
        if (binding.traineeCollege.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the College/University field.", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (binding.traineeDegree.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the Degree field.", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (binding.traineeMajor.text.toString().isEmpty()){
            Snackbar.make(view!!, "Please fill the Major field.", Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }

    fun checkName(name : String): Boolean{
        val namePattern : Regex = "[a-zA-Z]+".toRegex()
        if (name.matches(namePattern)){
            return true
        }
        return false
    }

    fun checkEmail(email : String): Boolean{
        val emailPattern : Regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        if (email.matches(emailPattern)){
            return true
        }
        return false
    }

    fun checkPhone(phone : String) : Boolean{
        val phonePattern : Regex = "[0-9]{3}+-[0-9]{3}+-+[0-9]{4}".toRegex()
        if (phone.matches(phonePattern)){
            return true
        }
        return false
    }

    fun checkAuto(autocomplete : String) : Boolean{
        val array : Array<String> = resources.getStringArray(R.array.TraineeStatus)
        array.forEach { it ->
            if (it.equals(autocomplete)){
                return true
            }
        }
        return false
    }
}