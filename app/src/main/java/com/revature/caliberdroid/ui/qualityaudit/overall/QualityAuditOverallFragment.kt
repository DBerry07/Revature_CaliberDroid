package com.revature.caliberdroid.ui.qualityaudit.overall

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.data.repository.CategoryRepository
import com.revature.caliberdroid.databinding.DialogAddAssessmentBinding
import com.revature.caliberdroid.databinding.DialogAddCategoriesBinding
import com.revature.caliberdroid.databinding.FragmentQualityAuditOverallBinding
import timber.log.Timber

class QualityAuditOverallFragment : Fragment(), DialogInterface.OnMultiChoiceClickListener {

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES: java.util.Comparator<SkillCategory> =
            Comparator { a, b -> a.category.compareTo(b.category) }
        @JvmField val ALPHABETICAL_COMPARATOR_CATEGORIES: java.util.Comparator<Category> =
            Comparator { a, b -> a.skillCategory.compareTo(b.skillCategory) }
    }

    private var _binding: FragmentQualityAuditOverallBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: QualityAuditOverallViewModel by activityViewModels()
    private val args: QualityAuditOverallFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQualityAuditOverallBinding.inflate(inflater)

        binding.auditWeekNotes = args.auditWeekNotesSelected
        binding.batch = args.batchSelected
        binding.statusHandler = StatusHandler(requireContext(), binding)

        binding.includeAuditoverallStatusChooserLayout.root.visibility = View.GONE

        viewModel.getSkillCategories(args.batchSelected, args.auditWeekNotesSelected.weekNumber)

        binding.rvAuditoverallCategories.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.rvAuditoverallCategories.adapter = SkillCategoryAdapter(requireContext(), ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES)

//        viewModel.loadCategories()
//        viewModel.categories.observe(viewLifecycleOwner, Observer {
//            binding.mssAuditoverallAddcatagories.setItems(it)
//        })

        binding.btnAuditoverallAddcategories.setOnClickListener(View.OnClickListener {

            val builder = AlertDialog.Builder(requireContext())


            viewModel.loadCategories()
            viewModel.categories.observe(viewLifecycleOwner, Observer {
                val itemNames = arrayOfNulls<String>(viewModel.categories.value!!.size)
                val selection = BooleanArray(viewModel.categories.value!!.size)
                for (i in it.indices) {
                    itemNames[i] = (it[i].skillCategory)
                    selection[i] = false
                }

                builder.setMultiChoiceItems(itemNames, selection, this)
            })

            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

            })
            builder.show()

//            val builder = AlertDialog.Builder(it.context)
//
//            builder.setTitle(resources.getString(R.string.add_categories))
//
//            val dialogBinding = DialogAddCategoriesBinding.inflate(inflater)
//
//            dialogBinding.rvAddcategoriesdialogCategories.layoutManager = LinearLayoutManager(requireContext())
//            viewModel.loadCategories()
//            viewModel.categories.observe(viewLifecycleOwner, Observer {
//                dialogBinding.rvAddcategoriesdialogCategories.adapter = AddCategoryAdapter(requireContext(), viewModel.categories.value!!)
//            })
//
//            builder.setView(dialogBinding.root)
//
//            builder.setPositiveButton(R.string.btn_add, DialogInterface.OnClickListener { dialog, which ->
//
//            })
//
//            builder.setNegativeButton(R.string.button_cancel, null)
//
//            val dialog = builder.create()
//
//            dialog.setContentView(dialogBinding.root)
//
//            dialog.show()
        })

        setClickListeners()

        subscribeToViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun subscribeToViewModel() {
        viewModel.skillCategoryLiveData.observe(viewLifecycleOwner, Observer {
            (binding.rvAuditoverallCategories.adapter as SkillCategoryAdapter).edit()
                .replaceAll(it)
                .commit()
        })
    }

    private fun setClickListeners() {
        binding.btnAuditoverallTrainees.setOnClickListener {
            findNavController().navigate(
                QualityAuditOverallFragmentDirections.actionQualityAuditOverallFragmentToQualityAuditTraineesFragment(
                    args.batchSelected,
                    args.auditWeekNotesSelected
                )
            )
        }

        binding.btnAuditoverallSave.setOnClickListener {
        }

        binding.imgAuditoverallOverallstatus.setOnClickListener {

        }
    }

    private fun showAddCategoriesDialog(view: View, inflater: LayoutInflater) {

        val builder = AlertDialog.Builder(view.context)

        builder.setTitle(resources.getString(R.string.add_categories))

        val dialogBinding = DialogAddCategoriesBinding.inflate(inflater)

//        dialogBinding.rvAddcategoriesdialogCategories.layoutManager = LinearLayoutManager(requireContext())
//        viewModel.loadCategories()
//        viewModel.categories.observe(viewLifecycleOwner, Observer {
//            dialogBinding.rvAddcategoriesdialogCategories.adapter = AddCategoryAdapter(requireContext(), viewModel.categories.value!!)
//        })

        builder.setView(dialogBinding.root)

        builder.setPositiveButton(R.string.btn_add, DialogInterface.OnClickListener { dialog, which ->

        })

        builder.setNegativeButton(R.string.button_cancel, null)

        val dialog = builder.create()

        dialog.show()

    }

    class StatusHandler(val context: Context, val binding: FragmentQualityAuditOverallBinding) {
        fun onFaceClick(view: View) {
            Toast.makeText(context, "Face clicked", Toast.LENGTH_SHORT).show()

            when (binding.includeAuditoverallStatusChooserLayout.root.visibility) {
                View.VISIBLE -> binding.includeAuditoverallStatusChooserLayout.root.visibility =
                    View.GONE
                else -> binding.includeAuditoverallStatusChooserLayout.root.visibility =
                    View.VISIBLE
            }
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int, isChecked: Boolean) {
        Timber.d(viewModel.categories.value!![which].skillCategory)
    }
}
