package com.revature.caliberdroid.ui.trainees

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.revature.caliberdroid.R

/**
 * A simple [Fragment] subclass.
 */
class AddTraineeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_add_trainee, container, false)

        val button = view.findViewById<Button>(R.id.TM_btn_create_trainee)

        button.setOnClickListener {
            createTrainee(view)
        }

        return view
    }

    fun createTrainee(view: View){
        
    }
}
