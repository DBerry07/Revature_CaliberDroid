package com.revature.caliberdroid.data.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Trainee
import com.revature.caliberdroid.data.parser.JSONParser
import com.revature.caliberdroid.util.DateConverter
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

        Timber.d(traineeData.toString())

        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/create"
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST,
            url,
            traineeData,
            Response.Listener<JSONObject> { response ->
                //Toast.makeText(APIHandler.context, "Trainee added successfully!", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )
        queue.add(jsonRequest)
        Toast.makeText(APIHandler.context, "Processing...", Toast.LENGTH_SHORT).show()
    }

    fun putTrainee(traineeData: JSONObject) {
        Timber.d(traineeData.toString())
        val queue = Volley.newRequestQueue(APIHandler.context)

        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/update"

        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            traineeData,
            Response.Listener<JSONObject> { response ->
                //Toast.makeText(APIHandler.context, "Trainee updated successfully!", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )
        queue.add(jsonRequest)
        Toast.makeText(APIHandler.context, "Processing...", Toast.LENGTH_SHORT).show()

    }

    fun putTrainee(context: Context, trainee: Trainee) {
        val queue = Volley.newRequestQueue(context)

        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/user/all/trainee/update"

        val traineeData = JSONObject()

        traineeData.apply {
            put("traineeId", trainee.traineeId)
            put("resourceId", trainee.resourceId)
            put("name", trainee.name)
            put("email", trainee.email)
            put("trainingStatus", trainee.trainingStatus)
            put("batchId", trainee.batchId)
            put("phoneNumber", trainee.phoneNumber)
            put("skypeId", trainee.skypeId)
            put("profileUrl", trainee.profileUrl)
            put("recruiterName", trainee.recruiterName)
            put("college", trainee.college)
            put("degree", trainee.degree)
            put("major", trainee.major)
            put("techScreenerName", trainee.techScreenerName)
            put("techScreenScore", trainee.techScreenScore)
            put("projectCompletion", trainee.projectCompletion)
            put("flagStatus", trainee.flagStatus)
            put("flagNotes", trainee.flagNotes)
            put("flagAuthor", trainee.flagAuthor)
            put("flagTimestamp", trainee.flagTimestamp)
            put("flagTimestamp", DateConverter.getDate(System.currentTimeMillis() / 1000))
        }

        val jsonRequest = JsonObjectRequest(
            Request.Method.PUT,
            url,
            traineeData,
            Response.Listener { response ->
                Timber.d("Response from trainee PUT -> $response")
            },
            Response.ErrorListener { error -> Timber.d(error.toString()) }
        )
        queue.add(jsonRequest)

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