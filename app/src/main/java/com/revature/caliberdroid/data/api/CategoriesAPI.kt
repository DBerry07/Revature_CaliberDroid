package com.revature.caliberdroid.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.parser.CategoryParser
import kotlin.collections.ArrayList

object CategoriesAPI {

    fun getCategories( liveData: MutableLiveData< ArrayList<Category> > ){
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/category/"
        val categoriesRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                liveData.postValue( CategoryParser.parseCategory(response) )
            },
            Response.ErrorListener { error ->
                Log.d("APIHandler","Error retrieving categories: "+error.toString())
            }
        )
        queue.add(categoriesRequest)
    }
}