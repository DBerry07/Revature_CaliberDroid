package com.revature.caliberdroid.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Location
import com.revature.caliberdroid.data.parser.LocationParser
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.lang.Exception

object LocationsAPI {

    fun getLocations(liveData: MutableLiveData<ArrayList<Location>>){
        var queue = Volley.newRequestQueue(APIHandler.context);
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/location/all/location/all";
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

    fun addLocation(location:Location){
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/location/vp/location/create"
        Timber.d("Url being sent: $url")
        val jsonBody =
            JSONObject(
                " {\"address\": \"" + location.address + "\", " +
                        "\"city\": \"" + location.city + "\", " +
                        "\"name\": \"" + location.name + "\", " +
                        "\"state\": \"" + location.state + "\", " +
                        "\"zipcode\": \"" + location.zipcode + "\", " +
                        "\"active\": \"" + location.active + "\"} "
            )
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: " + response.toString())
                try {
                } catch (e: Exception) {
                    Timber.d("error with live data ${e.toString()}")
                }
            },
            Response.ErrorListener { error ->
                Timber.d("Error retrieving categories: " + error.toString())
            }
        )
        queue.add(request)
    }

    fun editLocation(location:Location){
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/location/vp/location/update"
        Timber.d("Url being sent: $url")
        //id: 2054
        //name: "No longer Test Loc"
        //city: "Cheyanne"
        //zipcode: "13579"
        //address: "1234 Testing St"
        //state: "AR"
        //active: false
        val jsonBody =
            JSONObject(
                " {\"id\": \"" + location.locationID + "\", " +
                        "\"address\": \"" + location.address + "\", " +
                        "\"city\": \"" + location.city + "\", " +
                        "\"name\": \"" + location.name + "\", " +
                        "\"state\": \"" + location.state + "\", " +
                        "\"zipcode\": \"" + location.zipcode + "\", " +
                        "\"active\": \"" + location.active + "\"} "
            )
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest(
            Request.Method.PUT,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: " + response.toString())
                try {
                } catch (e: Exception) {
                    Timber.d("error with live data ${e.toString()}")
                }
            },
            Response.ErrorListener { error ->
                Timber.d("Error retrieving categories: " + error.toString())
            }
        )
        queue.add(request)
    }
}