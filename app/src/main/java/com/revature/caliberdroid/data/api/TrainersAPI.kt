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
import timber.log.Timber

object TrainersAPI {
    fun getTrainers(liveData: MutableLiveData<ArrayList<Trainer>>){
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainers/"
        val locationsRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray>{ response ->
                Timber.d("This is the response: ${TrainerParser.parseTrainer(response)}" )
                liveData.postValue(TrainerParser.parseTrainer(response))
            },
            Response.ErrorListener { error ->
                Timber.d("This is the error: $error")
            }
        )
        queue.add(locationsRequest)
    }
}