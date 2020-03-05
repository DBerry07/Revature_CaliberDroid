package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.revature.caliberdroid.R

/**
 * A simple [Fragment] subclass.
 */
class SwitchTraineeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_switch_trainee, container, false)
    }

}
