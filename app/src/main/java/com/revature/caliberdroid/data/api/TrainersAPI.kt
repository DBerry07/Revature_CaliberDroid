package com.revature.caliberdroid.data.api

import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.data.parser.TrainerParser
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.lang.Exception

object TrainersAPI {
    fun getTrainers(liveData: MutableLiveData<ArrayList<Trainer>>) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainers/"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                Timber.d("Get all trainers response: $response.toString()" )
                liveData.postValue(TrainerParser.parseTrainer(response))
            },
            Response.ErrorListener { error ->
                Timber.d("Get all trainers error: " + error.toString())
            }
        )
        queue.add(request)
    }

    fun addTrainer(trainer: Trainer) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainers/"
        Timber.d("Url being sent: $url")
        val jsonBody =
            JSONObject(
                " {\"name\": \"" + trainer.name + "\", " +
                        "\"email\": \"" + trainer.email + "\", " +
                        "\"title\": \"" + trainer.title + "\", " +
                        "\"tier\": \"" + trainer.tier + "\"} "
            )
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: $response")
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

    fun editTrainer(trainer: Trainer) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/trainers/" + trainer.trainerID
        Timber.d("Url being sent: $url")

        //trainerId: 3052
        //name: "Updated Again"
        //title: "Trainer"
        //email: "person914@email.com"
        //tier: "ROLE_INACTIVE"
        //password: null
        val jsonBody = JSONObject(
            " {\"trainerId\": " + trainer.trainerID + "," +
                    "\"name\": \"" + trainer.name + "\"," +
                    "\"email\": \"" + trainer.email + "\", " +
                    "\"title\": \"" + trainer.title + "\"," +
                    "\"tier\": \"" + trainer.tier + "\"," +
                    "\"password\": \"" + trainer.password + "\"} "
        )
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest(
            Request.Method.PUT,
            url,
            jsonBody,
            Response.Listener { response ->
                Timber.d("Response: $response")
            },
            Response.ErrorListener { error ->
                Timber.d("Error retrieving categories: $error")
            }
        )
        queue.add(request)
    }
}