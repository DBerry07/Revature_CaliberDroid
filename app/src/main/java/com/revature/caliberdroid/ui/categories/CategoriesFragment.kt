package com.revature.caliberdroid.ui.categories

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.revature.caliberdroid.R

import com.revature.caliberdroid.adapter.categories.CategoriesAdapter
import com.revature.caliberdroid.adapter.categories.listeners.EditCategoryListenerInterface
import com.revature.caliberdroid.adapter.categories.listeners.ToggleCategoryListenerInterface
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.databinding.FragmentCategoriesBinding

class CategoriesFragment: Fragment() {
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
                rvActiveCategories.adapter = CategoriesAdapter(activeCategories,EditCategoriesOnClickListener(),ToggleCategoryStatusOnClickListener())
                rvStaleCategories.adapter = CategoriesAdapter(inactiveCategories,EditCategoriesOnClickListener(),ToggleCategoryStatusOnClickListener())
            })
            btnAddCategory.setOnClickListener{
                var dialog: CategoriesDialog = CategoriesDialog(AddCategoriesListener(), R.layout.dialog_add_category)
                dialog.show(parentFragmentManager,"Add Category")
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

//DIALOG: OnClick Events for Add Category Dialog Buttons
    inner class AddCategoriesListener: CategoriesDialog.CategoriesDialogListener{
        override fun onDialogPositiveClick(dialog: DialogFragment){
            Log.d("CategoriesFragment","adding a category")
        }
        override fun onDialogNegativeClick(dialog: DialogFragment){
            Log.d("CategoriesFragment","cancel adding a category")
        }
    }

//DIALOG: OnClick Events for Edit Category Dialog Buttons
    inner class EditCategoriesItemListener: CategoriesDialog.CategoriesDialogListener{
        override fun onDialogPositiveClick(dialog: DialogFragment){
            Log.d("CategoriesFragment","editing a category")
        }
        override fun onDialogNegativeClick(dialog: DialogFragment){
            Log.d("CategoriesFragment","cancel editing a category")
        }
    }

    inner class ToggleCategoryStatusOnClickListener: ToggleCategoryListenerInterface{
        override fun onToggleCategory(category: Category) {
            Log.d("CategoriesFragment","will toggle the category")
        }
    }

    inner class EditCategoriesOnClickListener: EditCategoryListenerInterface{
        override fun onEditCategory(category: Category) {
            var dialog: CategoriesDialog = CategoriesDialog(EditCategoriesItemListener(), R.layout.dialog_edit_category)
//            var dialogObject = dialog.view!!
//            var field = dialogObject.findViewById<EditText>(R.id.tvDialogField)!!
//                field.setText(category.skillCategory)
            dialog.show(parentFragmentManager,"Edit Category")
        }
    }

}