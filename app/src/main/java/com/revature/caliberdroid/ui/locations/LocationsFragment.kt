package com.revature.caliberdroid.ui.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

import com.revature.caliberdroid.R


class LocationsFragment : Fragment(), View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?
    ) : View?{
        val root:ViewGroup = inflater.inflate(R.layout.fragment_locations,container,false) as ViewGroup
        root.findViewById<Button>(R.id.btnAddLocation).setOnClickListener(this)
        root.findViewById<Button>(R.id.btnEditLocation).setOnClickListener(this)
        return root
    }

    override fun onClick(view : View){
        when(view.id){
            R.id.btnAddLocation -> findNavController().navigate(R.id.action_locationsFragment_to_addLocationFragment)
            R.id.btnEditLocation -> findNavController().navigate(R.id.action_locationsFragment_to_editLocationFragment)
            else ->{}
        }
    }
}
