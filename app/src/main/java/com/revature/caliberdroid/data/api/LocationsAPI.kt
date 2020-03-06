package com.revature.caliberdroid.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.parser.LocationParser
import org.json.JSONArray

object LocationsAPI {

    fun getLocations(liveData: MutableLiveData<ArrayList<Location>>){
        var queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/location/all/location/all"
        val locationsRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray>{ response ->
                Log.d("Locations","This is the response: "+ LocationParser.parseLocation(response).toString())
                liveData.postValue(LocationParser.parseLocation(response))
            },
            Response.ErrorListener { error ->
                Log.d("APIHandler","This is the error: "+error.toString())
            }
        )
        queue.add(locationsRequest)
    }
}