package com.revature.caliberdroid.ui.qualityaudit.overall

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.databinding.FragmentQualityAuditOverallBinding
import com.revature.caliberdroid.ui.qualityaudit.StatusHandler
import com.revature.caliberdroid.util.KeyboardUtil
import timber.log.Timber

class QualityAuditOverallFragment : Fragment(), SkillCategoryAdapter.OnDeleteClickListener {

    companion object {
        @JvmField val ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES: java.util.Comparator<SkillCategory> =
            Comparator { a, b -> a.category.compareTo(b.category) }
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

        viewModel.batch = args.batchSelected
        viewModel.weekNumber = args.auditWeekNotesSelected.weekNumber

        viewModel.getSkillCategories(args.batchSelected, args.auditWeekNotesSelected.weekNumber)

        initStatusChooser()

        initRecyclerView()

        setClickListeners()

        watchOverallNote()

        subscribeToViewModel()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.rvAuditoverallCategories.layoutManager =
            LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        binding.rvAuditoverallCategories.adapter =
            SkillCategoryAdapter(requireContext(), ALPHABETICAL_COMPARATOR_SKILL_CATEGORIES, this)
    }

    private fun initStatusChooser() {
        val statusHandler = OverallStatusHandler(requireContext(), binding, viewModel)
        binding.overallStatusHandler = statusHandler
        binding.includeAuditoverallStatusChooserLayout.statusHandler = statusHandler

        binding.includeAuditoverallStatusChooserLayout.root.visibility = View.GONE
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

        binding.root.setOnClickListener {
            binding.root.clearFocus()
        }
    }

    private fun showAddCategoriesDialog() {

        val builder = MaterialAlertDialogBuilder(requireContext())
        builder.setTitle(R.string.add_categories)

        val itemNames = viewModel.getActiveCategoryNames()
        val selections = viewModel.getCategoryBooleanArray()

        builder.setMultiChoiceItems(
            itemNames,
            selections
        ) { _, _, _ -> }

        builder.setPositiveButton(R.string.btn_add) { _, _ ->

            val categoriesToAdd: ArrayList<Category> = arrayListOf()

            for (i in selections.indices) {
                if (selections[i]) categoriesToAdd.add(viewModel.categories.value!![i])
            }

            viewModel.updateAuditCategories(categoriesToAdd)
        }

        builder.setNegativeButton(R.string.btn_cancel) { _, _ -> }

        builder.show()
    }

    private fun watchOverallNote() {
        binding.etAuditoverallOverallfeedback.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                KeyboardUtil.hideSoftKeyboard(requireContext(), v)
                viewModel.putAuditWeekNotes(binding.auditWeekNotes!!)
            }
        }

        binding.etAuditoverallOverallfeedback.addTextChangedListener {
            if (binding.auditWeekNotes!!.overallNotes != it.toString()) {
                binding.auditWeekNotes!!.overallNotes = it.toString()
                viewModel.startDelayedSaveThread(
                    binding.auditWeekNotes!!,
                    viewModel::putAuditWeekNotes
                )
            }
        }
    }

    class OverallStatusHandler(
        val context: Context,
        val binding: FragmentQualityAuditOverallBinding,
        val viewModel: QualityAuditOverallViewModel
    ) : StatusHandler {
        override fun onStatusClick(view: View) {
            binding.includeAuditoverallStatusChooserLayout.root.visibility =
                when (binding.includeAuditoverallStatusChooserLayout.root.visibility) {
                    View.VISIBLE -> View.GONE
                    else -> View.VISIBLE
            }
        }

        override fun onStatusChoiceClick(view: View) {
            Timber.d("$view clicked")
            val previous = binding.auditWeekNotes!!.overallStatus

            binding.auditWeekNotes!!.overallStatus = when (view.id) {
                R.id.img_chooserstatus_undefined -> "Undefined"
                R.id.img_chooserstatus_poor -> "Poor"
                R.id.img_chooserstatus_average -> "Average"
                R.id.img_chooserstatus_good -> "Good"
                else -> "Superstar"
            }
            if (previous != binding.auditWeekNotes!!.overallStatus) {
                viewModel.putAuditWeekNotes(binding.auditWeekNotes!!)
            }
            binding.includeAuditoverallStatusChooserLayout.root.visibility = View.GONE
        }
    }

    override fun onSkillDeleteClicked(skillCategory: SkillCategory) {
        viewModel.deleteAuditCategory(skillCategory)
    }
}
