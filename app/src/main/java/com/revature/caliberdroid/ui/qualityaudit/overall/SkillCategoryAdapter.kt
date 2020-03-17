package com.revature.caliberdroid.ui.qualityaudit.overall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.databinding.ItemSkillCategoryBinding

class SkillCategoryAdapter(context: Context,
                           comparator: Comparator<SkillCategory>,
                           val listener: OnDeleteClickListener
) : SortedListAdapter<SkillCategory>(context, SkillCategory::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): SkillCategoryViewHolder {
        return SkillCategoryViewHolder(ItemSkillCategoryBinding.inflate(inflater,parent,false), listener)
    }

    class SkillCategoryViewHolder(val binding: ItemSkillCategoryBinding, val listener: OnDeleteClickListener)
        : SortedListAdapter.ViewHolder<SkillCategory>(binding.root), View.OnClickListener {

        override fun performBind(item: SkillCategory) {
            binding.skillCategory = item
            binding.imgItemskillcategoryDelete.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            listener.onSkillDeleteClicked(binding.skillCategory!!)
        }

    }

    interface OnDeleteClickListener {
        fun onSkillDeleteClicked(skillCategory: SkillCategory)
    }

}