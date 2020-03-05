package com.revature.caliberdroid.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Category
import com.revature.caliberdroid.data.parser.CategoryParser
import org.json.JSONObject
import kotlin.collections.ArrayList
import timber.log.Timber
import java.lang.Exception
import java.lang.NullPointerException

object CategoriesAPI {

    fun getCategories(liveData: MutableLiveData<ArrayList<Category>>) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/category/"
        val categoriesRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener { response ->
                liveData.postValue(CategoryParser.parseCategoryList(response))
            },
            Response.ErrorListener { error ->
                Log.d("APIHandler", "Error retrieving categories: " + error.toString())
            }
        )
        queue.add(categoriesRequest)
    }

    fun addCategory(skillCategory: String, liveData: MutableLiveData<ArrayList<Category>>) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/category/"
        val jsonBody = JSONObject(" {\"categoryId\": 0, \"skillCategory\": $skillCategory, \"categoryOwner\": \"1\", \"active\": true} ")

        val categoriesRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: " + response.toString())
                try {
                    liveData.value!!.add( CategoryParser.parseSingleCategory(response) )
                    liveData.postValue(liveData.value)
                }catch (e:Exception){
                    Timber.d("error with live data ${e.toString()}")
                }
            },
            Response.ErrorListener { error ->
                Timber.d("Error retrieving categories: " + error.toString())
            }
        )
        queue.add(categoriesRequest)
    }

    fun editCategory(category: Category, liveData: MutableLiveData<ArrayList<Category>>){
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/category/"+category.categoryId
        Timber.d("Url being sent: $url")
        val jsonBody = JSONObject(" {\"categoryId\": \""+category.categoryId+"\", \"skillCategory\": \""+category.skillCategory+"\", \"active\": "+category.active+" } ")
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val categoriesRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: " + response.toString())
                try {
                    var returnedCategory = CategoryParser.parseSingleCategory(response)
                    var categories: ArrayList<Category> = liveData.value!!
                    for(i in 0 until categories.size){
                        var current:Category = categories.get(i)
                        if(current.categoryId == returnedCategory.categoryId){
                            current = returnedCategory
                        }
                    }
                    liveData.postValue( categories )
                }catch (e:Exception){
                    Timber.d("error with live data ${e.toString()}")
                }
            },
            Response.ErrorListener { error ->
                Timber.d("Error retrieving categories:\n"+error.toString())
            }
        )
        queue.add(categoriesRequest)
    }
}