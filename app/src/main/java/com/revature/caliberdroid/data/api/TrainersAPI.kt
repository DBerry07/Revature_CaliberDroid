package com.revature.caliberdroid.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.parser.CategoryParser
import com.revature.caliberdroid.data.parser.LocationParser
import com.revature.caliberdroid.data.parser.TrainerParser
import com.revature.caliberdroid.data.repository.TrainerRepository
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.lang.Exception

object TrainersAPI {
    fun getTrainers(liveData: MutableLiveData<ArrayList<Trainer>>){
        var queue = Volley.newRequestQueue(APIHandler.context);
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainers/";
        val locationsRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray>{ response ->
                Log.d("Locations","This is the response: "+ TrainerParser.parseTrainer(response).toString())
                liveData.postValue(TrainerParser.parseTrainer(response))
            },
            Response.ErrorListener { error ->
                Log.d("APIHandler","This is the error: "+error.toString())
            }
        )
        queue.add(locationsRequest)
    }

    fun addTrainer(trainer:Trainer,liveData: MutableLiveData< ArrayList<Trainer> >){
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainers/"
        Timber.d("Url being sent: $url")
        val jsonBody = JSONObject(" {\"name\": \""+trainer.name+"\", \"email\": \""+trainer.email+"\", \"title\": \""+trainer.title+"\", \"tier\": \""+trainer.tier+"\"} ")
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: " + response.toString())
                try {
                }catch (e: Exception){
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