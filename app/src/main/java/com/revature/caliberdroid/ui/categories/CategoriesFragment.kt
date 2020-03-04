package com.revature.caliberdroid.ui.categories

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.categories.CategoriesAdapter
import com.revature.caliberdroid.databinding.FragmentCategoriesBinding

class CategoriesFragment: Fragment(){
    var _binding:FragmentCategoriesBinding? = null
    val binding get() = _binding!!
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):View?{
        categoriesViewModel.getCategories()
        _binding = FragmentCategoriesBinding.inflate(layoutInflater)
        binding.apply {
            setLifecycleOwner(this@CategoriesFragment)
            categoriesViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer { categories ->
                rvCategories.adapter = CategoriesAdapter(categories)
            })
        }

        return binding.root
    }

}