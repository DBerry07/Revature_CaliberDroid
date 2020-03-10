package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentSwitchTraineeBinding
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class SwitchTraineeFragment : Fragment() {
    private var _binding: FragmentSwitchTraineeBinding? = null
    private val binding get() = _binding!!
    private val args: SwitchTraineeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val traineeToSwitch = args.traineeToSwitch
        Timber.d("Trainee to Switch: "+traineeToSwitch)
        _binding = FragmentSwitchTraineeBinding.inflate(layoutInflater, container, false)

        //binding.

        return binding.root
    }

}
