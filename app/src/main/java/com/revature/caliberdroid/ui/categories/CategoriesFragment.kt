package com.revature.caliberdroid.ui.categories

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer

import com.revature.caliberdroid.adapter.categories.CategoriesAdapter
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.databinding.FragmentCategoriesBinding

class CategoriesFragment: Fragment(), CategoriesDialog.CategoriesDialogListener {
    var _binding:FragmentCategoriesBinding? = null
    val binding get() = _binding!!
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    val activeCategories:ArrayList<Category> = ArrayList()
    val inactiveCategories:ArrayList<Category> = ArrayList()

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
                sortCategories(categories)
                rvActiveCategories.adapter = CategoriesAdapter(activeCategories)
                rvStaleCategories.adapter = CategoriesAdapter(inactiveCategories)
            })
            btnAddCategory.setOnClickListener{
                var dialog: CategoriesDialog = CategoriesDialog()
                dialog.show(parentFragmentManager,"Title")
            }
        }

        return binding.root
    }

    fun sortCategories( categories: ArrayList<Category> ){

        for(category in categories){
            if(category.active){
                activeCategories.add(category)
            }else{
                inactiveCategories.add(category)
            }
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment){

    }
    override fun onDialogNegativeClick(dialog: DialogFragment){

    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}