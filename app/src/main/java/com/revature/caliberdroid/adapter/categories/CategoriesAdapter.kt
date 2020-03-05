package com.revature.caliberdroid.adapter.categories

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.categories.listeners.EditCategoryListenerInterface
import com.revature.caliberdroid.adapter.categories.listeners.ToggleCategoryListenerInterface
import com.revature.caliberdroid.data.model.Category

class CategoriesAdapter(val categories: ArrayList<Category>, val editCategoryListener: EditCategoryListenerInterface, val toggleCategoryClickListener: ToggleCategoryListenerInterface): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvCategory:TextView = itemView.findViewById(R.id.tvCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        var view:View  = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        var category: Category = categories.get(position)
        holder.tvCategory.text = category.skillCategory
        //Edit
        holder.tvCategory.setOnLongClickListener {
            editCategoryListener.onEditCategory(category)
            true
        }
        //Toggle
        holder.tvCategory.setOnClickListener{
            toggleCategoryClickListener.onToggleCategory(category)
        }
    }
}