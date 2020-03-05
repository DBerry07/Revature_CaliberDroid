package com.revature.caliberdroid.data.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.parser.LocationParser
import com.revature.caliberdroid.data.parser.TrainerParser
import org.json.JSONArray

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
}