package com.revature.caliberdroid.data.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.parser.JSONParser
import org.json.JSONArray

object APIHandler {

    lateinit var context: Context

    fun getBatches(liveData: MutableLiveData<Batch>) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/vp/batch/all/?year=2020&quarter=1"
// Request a string response from the provided URL.
        val stringRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                // Display the first 500 characters of the response string.
                liveData.postValue(JSONParser.parseBatch(response))
            },
            Response.ErrorListener { error -> Log.d("APIHandler", error.toString()) })
// Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}