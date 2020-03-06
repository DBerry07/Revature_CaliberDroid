package com.revature.caliberdroid.ui.qualityaudit.overall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.AuditWeekNotes
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.databinding.ItemSkillCategoryBinding

class SkillCategoryAdapter(context: Context,
                           comparator: Comparator<SkillCategory>
) : SortedListAdapter<SkillCategory>(context, SkillCategory::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): SkillCategoryViewHolder {
        return SkillCategoryViewHolder(ItemSkillCategoryBinding.inflate(inflater,parent,false))
    }

    class SkillCategoryViewHolder(val binding: ItemSkillCategoryBinding)
        : SortedListAdapter.ViewHolder<SkillCategory>(binding.root){

        override fun performBind(item: SkillCategory) {
            binding.skillCategory = item
        }

    }

    interface OnItemClickListener {
        fun onSkillCategoryClick(skillCategory: SkillCategory)
    }

}