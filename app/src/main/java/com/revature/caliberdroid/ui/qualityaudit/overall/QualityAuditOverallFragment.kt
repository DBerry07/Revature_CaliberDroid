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
import com.revature.caliberdroid.databinding.FragmentQualityAuditOverallBinding

class QualityAuditOverallFragment : Fragment() {

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

        viewModel.loadCategories()

        binding.auditWeekNotes = args.auditWeekNotesSelected
        binding.batch = args.batchSelected
        binding.statusHandler = StatusHandler(requireContext(), binding)

        binding.includeAuditoverallStatusChooserLayout.root.visibility = View.GONE

        viewModel.batch = args.batchSelected
        viewModel.weekNumber = args.auditWeekNotesSelected.weekNumber

        viewModel.getSkillCategories(args.batchSelected, args.auditWeekNotesSelected.weekNumber)

        binding.rvAuditoverallCategories.layoutManager = LinearLayoutManager(requireContext()).apply {
            orientation = LinearLayoutManager.HORIZONTAL
        }
        binding.rvAuditoverallCategories.adapter = SkillCategoryAdapter(requireContext(), ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES)

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
            binding.tvAuditoverallNocategoriesmessage.visibility = if (it.size == 0) View.VISIBLE else View.GONE
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

        binding.btnAuditoverallAddcategories.setOnClickListener { showAddCategoriesDialog() }

        binding.btnAuditoverallSave.setOnClickListener { }

        binding.imgAuditoverallOverallstatus.setOnClickListener { }
    }

    private fun showAddCategoriesDialog() {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.add_categories)

        val itemNames = viewModel.getActiveCategoryNames()
        val selections = viewModel.getCategoryBooleanArray()

        builder.setMultiChoiceItems(itemNames, selections, DialogInterface.OnMultiChoiceClickListener { _, _, _ ->  })

        builder.setPositiveButton(R.string.btn_add, DialogInterface.OnClickListener { _, _ ->

            val categoriesToAdd: ArrayList<Category> = arrayListOf()

            for (i in selections.indices) {
                if (selections[i]) categoriesToAdd.add(viewModel.categories.value!![i])
            }

            viewModel.updateAuditCategories(categoriesToAdd)
        })

        builder.setNegativeButton(R.string.btn_cancel, DialogInterface.OnClickListener { _, _ ->  })

        builder.show()

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

}
