package com.revature.caliberdroid.ui.batches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.revature.caliberdroid.R

/**
 * A simple [Fragment] subclass.
 */
class ManageBatchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.fragment_manage_batch, container, false)
        var button : Button = v.findViewById(R.id.MB_btn_goto_batch_details)

        button.setOnClickListener {
            val navController = Navigation.findNavController(v)
            Toast.makeText(v.context, "BUTTON", Toast.LENGTH_LONG).show()
            navController.navigate(R.id.action_manageBatchFragment_to_batchDetailsFragment)
        }

        return v
    }
}
