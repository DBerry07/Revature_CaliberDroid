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
import kotlinx.android.synthetic.main.fragment_settings_categories.*
import timber.log.Timber

class CategoriesFragment : Fragment() {
    var _binding: FragmentSettingsCategoriesBinding? = null
    val binding get() = _binding!!
    private val categoriesViewModel: CategoriesViewModel by activityViewModels()
    val activeCategories: ArrayList<Category> = ArrayList()
    val inactiveCategories: ArrayList<Category> = ArrayList()
    lateinit var addCategoryDialogView: View
    lateinit var editCategoryDialogView: View


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
        binding.apply {
            setLifecycleOwner(this@CategoriesFragment)
            categoriesViewModel.categoryLiveData.observe(
                viewLifecycleOwner,
                Observer { categories ->
                    sortCategories(categories)
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
                var builder: AlertDialog.Builder = AlertDialog.Builder(context);
                addCategoryDialogView = LayoutInflater.from(context).inflate(
                    R.layout.dialog_add_category,
                    view!!.findViewById(android.R.id.content)
                )
                builder.setView(addCategoryDialogView)
                    .setPositiveButton(R.string.btn_add,
                        DialogInterface.OnClickListener { dialog, id ->
                            var etField = addCategoryDialogView.findViewById<EditText>(R.id.tvDialogField)
                            var entry:String = etField.text.toString()
                            CategoryRepository.addCategory(entry,categoriesViewModel.categoryLiveData)
                        }
                    )
                    .setNegativeButton(R.string.btn_cancel,
                        DialogInterface.OnClickListener { dialog, id ->

                        }
                    )
                var alertDialog: AlertDialog = builder.create();
                alertDialog.show()
            }
        }

        return binding.root
    }

    fun sortCategories(categories: ArrayList<Category>) {
        activeCategories.clear()
        inactiveCategories.clear()
        for (category in categories) {
            if (category.active) {
                activeCategories.add(category)
            } else {
                inactiveCategories.add(category)
            }
        }
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
            builder.setView(editCategoryDialogView)
                .setPositiveButton(R.string.btn_confirm,
                    DialogInterface.OnClickListener { dialog, id ->
                        category.skillCategory = editCategoryDialogView.findViewById<EditText>(R.id.tvDialogField).text.toString()
                        CategoryRepository.editCategory(category,categoriesViewModel.categoryLiveData)
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