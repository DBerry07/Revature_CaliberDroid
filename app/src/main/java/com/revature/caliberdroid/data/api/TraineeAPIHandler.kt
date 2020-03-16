package com.revature.caliberdroid.data.api

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.ui.trainees.TraineeFragment
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

object TraineeAPIHandler {

    fun getTrainees(liveData: MutableLiveData<List<Trainee>>,batchId:Long) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(APIHandler.context)
        //response is JSONarray of assessments
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/?batch=$batchId"
        val arrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener<JSONArray> { response ->
                liveData.postValue(JSONParser.parseTrainees(response))
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) })

        queue.add(arrayRequest)
    }

    fun postTrainee(traineeData: JSONObject) {
        val queue = Volley.newRequestQueue(APIHandler.context)

        Log.d("TraineeRequest", traineeData.toString())

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/create"
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            traineeData,
            Response.Listener<JSONObject> {response ->
                    //Toast.makeText(APIHandler.context, "Trainee added successfully!", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )
        queue.add(jsonRequest)
        Toast.makeText(APIHandler.context, "Processing...", Toast.LENGTH_SHORT).show()
    }

    fun putTrainee(traineeData: JSONObject) {
        Log.d("updateTrainee", traineeData.toString())
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/update"

        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            traineeData,
            Response.Listener<JSONObject> {response ->
                //Toast.makeText(APIHandler.context, "Trainee updated successfully!", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )
        queue.add(jsonRequest)
        Toast.makeText(APIHandler.context, "Processing...", Toast.LENGTH_SHORT).show()

    }

    fun deleteTrainee(trainee : Trainee) {
        Log.d("deleteTrainee", trainee.traineeId.toString())
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/delete/${trainee.traineeId}"

        val jsonRequest = JsonObjectRequest(
            Request.Method.DELETE,
            url,
            null,
            Response.Listener<JSONObject> {response ->
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )
        queue.add(jsonRequest)
    }

}