package com.revature.caliberdroid.ui.categories

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.revature.caliberdroid.R

import com.revature.caliberdroid.adapter.categories.CategoriesAdapter
import com.revature.caliberdroid.adapter.categories.listeners.EditCategoryListenerInterface
import com.revature.caliberdroid.adapter.categories.listeners.ToggleCategoryListenerInterface
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.repository.CategoryRepository
import com.revature.caliberdroid.databinding.FragmentSettingsCategoriesBinding
import com.revature.caliberdroid.util.DialogInvalidInput
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class CategoriesFragment : Fragment() {
    var _binding: FragmentSettingsCategoriesBinding? = null
    val binding get() = _binding!!
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    val activeCategories: ArrayList<Category> = ArrayList()
    val inactiveCategories: ArrayList<Category> = ArrayList()
    lateinit var addCategoryDialogView: View
    lateinit var editCategoryDialogView: View
    val validationString = StringBuilder()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        categoriesViewModel.getCategories()
        _binding = FragmentSettingsCategoriesBinding.inflate(layoutInflater)
        binding.activeCount = 0
        binding.staleCount = 0
        binding.apply {
            setLifecycleOwner(this@CategoriesFragment)
            categoriesViewModel.categoryLiveData.observe(
                viewLifecycleOwner,
                Observer { categories ->
                    divideActiveAndInactiveCategories(categories)
                    rvActiveCategories.adapter = CategoriesAdapter(
                        activeCategories,
                        EditCategoriesOnClickListener(),
                        ToggleCategoryStatusOnClickListener()
                    )
                    rvStaleCategories.adapter = CategoriesAdapter(
                        inactiveCategories,
                        EditCategoriesOnClickListener(),
                        ToggleCategoryStatusOnClickListener()
                    )
                })
            btnAddCategory.setOnClickListener {
                val builder: AlertDialog.Builder = AlertDialog.Builder(context);
                addCategoryDialogView = LayoutInflater.from(context).inflate(
                    R.layout.dialog_add_category,
                    view!!.findViewById(android.R.id.content)
                )
                val etField = addCategoryDialogView.findViewById<EditText>(R.id.tvDialogField)
                builder.setView(addCategoryDialogView)
                    .setPositiveButton(R.string.btn_add,
                        DialogInterface.OnClickListener { dialog, id ->
                            val entry = etField.text.toString()
                            if( CategoriesFieldValidator.validateFields(validationString,entry) ){
                                Timber.d("Category validation passed")
                                CategoryRepository.addCategory(entry,categoriesViewModel.categoryLiveData)
                            }else{
                                Timber.d("Category validation failed")
                            }
                        }
                    )
                    .setNegativeButton(R.string.btn_cancel,
                        DialogInterface.OnClickListener { dialog, id ->
                            DialogInvalidInput().showInvalidInputDialog(context,view,validationString.toString())
                        }
                    )
                val alertDialog: AlertDialog = builder.create()
                alertDialog.show()
            }
        }
        return binding.root
    }

    fun divideActiveAndInactiveCategories(_categories: ArrayList<Category>) {
        val categories = sortCategories(_categories)
        activeCategories.clear()
        inactiveCategories.clear()
        for (category in categories) {
            if (category.active) {
                activeCategories.add(category)
            } else {
                inactiveCategories.add(category)
            }
        }
        binding.activeCount = activeCategories.size
        binding.staleCount = inactiveCategories.size
    }

    private fun sortCategories(categories: ArrayList<Category>): ArrayList<Category>{
        Collections.sort(categories, object: Comparator<Category>{
            override fun compare(o1: Category?, o2: Category?): Int {
                return if(o1 != null && o2 != null){
                    o1.skillCategory.toLowerCase().compareTo(o2.skillCategory.toLowerCase())
                }else{
                    0
                }
            }
        })
        return categories
    }
    inner class ToggleCategoryStatusOnClickListener : ToggleCategoryListenerInterface {
        override fun onToggleCategory(category: Category) {
            category.active = !category.active
            CategoryRepository.editCategory(category,categoriesViewModel.categoryLiveData)
        }
    }

    inner class EditCategoriesOnClickListener : EditCategoryListenerInterface {
        override fun onEditCategory(category: Category) {
            var builder: AlertDialog.Builder = AlertDialog.Builder(context);
            editCategoryDialogView = LayoutInflater.from(context).inflate(
                R.layout.dialog_edit_category,
                view!!.findViewById(android.R.id.content)
            )
            val etField = editCategoryDialogView.findViewById<EditText>(R.id.tvDialogField)
            builder.setView(editCategoryDialogView)
                .setPositiveButton(R.string.btn_confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        val entry = etField.text.toString()
                        if(CategoriesFieldValidator.validateFields(validationString,entry)){
                            category.skillCategory = entry
                            CategoryRepository.editCategory(category,categoriesViewModel.categoryLiveData)
                        }else{
                            Timber.d("Category validation failed")
                            DialogInvalidInput().showInvalidInputDialog(context,view,validationString.toString())
                        }
                    }
                )
                .setNegativeButton(R.string.btn_cancel,
                    DialogInterface.OnClickListener { dialog, id ->

                    }
                )
            editCategoryDialogView.findViewById<EditText>(R.id.tvDialogField).setText(category.skillCategory)
            var alertDialog: AlertDialog = builder.create();
            alertDialog.show()
        }
    }

}