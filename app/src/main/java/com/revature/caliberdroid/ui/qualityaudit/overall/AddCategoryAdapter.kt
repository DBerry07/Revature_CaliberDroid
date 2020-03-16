package com.revature.caliberdroid.ui.qualityaudit.overall

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.databinding.ItemAddcategoriesdialogCategoryBinding


class AddCategoryAdapter(val context: Context, val categories: ArrayList<Category>)
    : RecyclerView.Adapter<AddCategoryAdapter.CategoryViewHolder>() {


    class CategoryViewHolder(val binding: ItemAddcategoriesdialogCategoryBinding)
            : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(ItemAddcategoriesdialogCategoryBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.binding.category = categories[position]

    }
}


//class AddCategoryAdapter(context: Context,
//                         comparator: Comparator<Category>
//) : SortedListAdapter<Category>(context, Category::class.java, comparator) {
//
//    override fun onCreateViewHolder(
//        inflater: LayoutInflater,
//        parent: ViewGroup,
//        viewType: Int
//    ): CategoryViewHolder {
//        return CategoryViewHolder(ItemAddcategoriesdialogCategoryBinding.inflate(inflater,parent,false))
//    }
//
//    class CategoryViewHolder(val binding: ItemAddcategoriesdialogCategoryBinding)
//        : SortedListAdapter.ViewHolder<Category>(binding.root){
//
//        override fun performBind(item: Category) {
//            binding.category = item
//        }
//
//    }
//
//    interface OnItemClickListener {
//        fun onCategoryClick(category: Category)
//    }
//}