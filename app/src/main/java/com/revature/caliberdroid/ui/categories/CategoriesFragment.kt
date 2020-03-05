package com.revature.caliberdroid.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.revature.caliberdroid.R
import com.revature.caliberdroid.databinding.FragmentCategoriesBinding

class CategoriesFragment: Fragment(){
    var _binding:FragmentCategoriesBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View?{
        _binding = FragmentCategoriesBinding.inflate(layoutInflater)
        binding.apply {

        }

        return binding.root
    }

}