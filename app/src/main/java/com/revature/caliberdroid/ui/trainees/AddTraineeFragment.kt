package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.databinding.FragmentAddTraineeBinding
import org.json.JSONObject
import java.util.*


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
                if (createTrainee()) {
                    findNavController().navigateUp()
                }
                //Shouldn't navigate back using the way you have below
                //This method will create problems when user presses the back button
                    //findNavController().navigate(AddTraineeFragmentDirections.actionAddTraineeFragmentToTraineeFragment( currentBatch ))
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter(
                context!!,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.TraineeStatus)
            )
            val autoCompleteTextView: AutoCompleteTextView = binding.traineeStatus
            autoCompleteTextView.threshold = 0
            autoCompleteTextView.setAdapter(adapter)
        }

        return view
    }

    fun createTrainee() : Boolean{
        if (formCheck() == false){
            return false
        }
        var name : String = "${binding.traineeLastName.text.toString()}, ${binding.traineeFirstName.text.toString()}"
        var jsonObject = JSONObject()
        jsonObject.put("firstName", binding.traineeFirstName.text.toString())
        jsonObject.put("lastName", binding.traineeLastName.text.toString())
        jsonObject.put("email", binding.traineeEmail.text.toString())
        jsonObject.put("trainingStatus", binding.traineeStatus.text.toString())
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
        Snackbar.make(binding.root, "Trainee added successfully!", Snackbar.LENGTH_LONG).show()
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
