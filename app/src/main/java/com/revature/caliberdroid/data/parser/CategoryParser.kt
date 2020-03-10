package com.revature.caliberdroid.data.parser

import com.revature.caliberdroid.data.model.Category
import org.json.JSONArray
import org.json.JSONObject
import kotlin.collections.ArrayList

object CategoryParser {

    fun parseCategoryList(response: JSONArray): ArrayList<Category>{
        val categories: ArrayList<Category> = ArrayList()
        for( i in 0 until response.length()){
            var category = response.getJSONObject(i)
            categories.add( parseSingleCategory(category) )
        }
        return categories
    }

    fun parseSingleCategory(response: JSONObject): Category{
        return  Category(
            response.getLong("categoryId"),
            response.getString("skillCategory"),
            response.getBoolean("active")
        )
    }
}