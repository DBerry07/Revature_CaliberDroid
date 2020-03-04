package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.Category
import org.json.JSONArray
import kotlin.collections.ArrayList

object CategoryParser {

    fun parseCategory(response: JSONArray): ArrayList<Category>{
        val categories: ArrayList<Category> = ArrayList()
        for( i in 0 until response.length()){
            var category = response.getJSONObject(i)
            categories.add( Category(
                category.getLong("categoryId"),
                category.getString("skillCategory"),
                category.getBoolean("active")
            ) )
        }
        return categories
    }
}