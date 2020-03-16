package com.revature.caliberdroid.adapter.categories

import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.revature.caliberdroid.R
import com.revature.caliberdroid.adapter.categories.listeners.EditCategoryListenerInterface
import com.revature.caliberdroid.adapter.categories.listeners.ToggleCategoryListenerInterface
import com.revature.caliberdroid.data.model.Category

class CategoriesAdapter(val categories: ArrayList<Category>, val editCategoryListener: EditCategoryListenerInterface, val toggleCategoryClickListener: ToggleCategoryListenerInterface): RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    class CategoriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvCategory:TextView = itemView.findViewById(R.id.tvCategory)
        val imgStatusIcon:ImageView = itemView.findViewById(R.id.imgStatusIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        var view:View  = LayoutInflater.from(parent.context).inflate(R.layout.item_settings_category,parent,false)
        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        var category: Category = categories.get(position)
        holder.tvCategory.text = category.skillCategory
        setStatusIcon(category.active, holder.imgStatusIcon)
        //Edit
        holder.itemView.setOnLongClickListener {
            editCategoryListener.onEditCategory(category)
            true
        }
        //Toggle
        holder.itemView.setOnClickListener{
            toggleCategoryClickListener.onToggleCategory(category)
            setStatusIcon(!category.active, holder.imgStatusIcon)
        }
    }

    private fun setStatusIcon(categoryStatus: Boolean, imgView: ImageView){
        if(!categoryStatus){
            imgView.setImageResource(R.drawable.ic_add_orange)
        }else{
            imgView.setImageResource(R.drawable.ic_remove_red)
        }
    }
}