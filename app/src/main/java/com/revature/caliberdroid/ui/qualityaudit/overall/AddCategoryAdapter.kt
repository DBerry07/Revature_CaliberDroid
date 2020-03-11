package com.revature.caliberdroid.ui.qualityaudit.overall

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.model.SkillCategory
import com.revature.caliberdroid.databinding.ItemAddcategoriesdialogCategoryBinding

class AddCategoryAdapter(context: Context,
                         comparator: Comparator<Category>
) : SortedListAdapter<Category>(context, Category::class.java, comparator) {

    override fun onCreateViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        return CategoryViewHolder(ItemAddcategoriesdialogCategoryBinding.inflate(inflater,parent,false))
    }

    class CategoryViewHolder(val binding: ItemAddcategoriesdialogCategoryBinding)
        : SortedListAdapter.ViewHolder<Category>(binding.root){

        override fun performBind(item: Category) {
            binding.skillCategory = item
        }

    }

    interface OnItemClickListener {
        fun onCategoryClick(skillCategory: SkillCategory)
    }
}