package com.revature.caliberdroid.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.revature.caliberdroid.data.model.Batch
import com.revature.caliberdroid.data.model.Trainer
import com.revature.caliberdroid.util.DateConverter
import org.json.JSONObject
import timber.log.Timber
import java.lang.Exception

object BatchAPIHandler {

    fun addBatch(batch: Batch) {
        val tempStartDate = DateConverter.getDate(batch._startDate)
        val tempEndDate = DateConverter.getDate(batch._endDate)

        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/all/batch/update/"
        Timber.d("Url being sent: $url")
        val jsonBody =
            JSONObject( "{ \"trainingName\": \"" + batch.trainingName + "\"," +
                        "\"trainingType\": \"" + batch.trainingType + "\"," +
                        "\"skillType\": \"" + batch.skillType + "\", " +
                        "\"trainer\": \"" + batch.trainerName + "\"," +
                        "\"coTrainer\": \"" + batch.coTrainerName + "\"," +
                        "\"startDate\": \"" + DateConverter.postDateString(tempStartDate) + "\", " +
                        "\"endDate\": \"" + DateConverter.postDateString(tempEndDate) + "\", " +
                        "\"goodGrade\": " + batch.goodGrade + ", " +
                        "\"passingGrade\": " + batch.passingGrade + ", " +
                        "\"location\": \"" + batch.location + "\" }"
            )
        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest(
            Request.Method.PUT,
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
                Timber.d("Error adding batch: %s", error.toString())
            }
        )
        queue.add(request)
    }

    fun editBatch(batch: Batch) {
        val tempStartDate = DateConverter.getDate(batch._startDate)
        val tempEndDate = DateConverter.getDate(batch._endDate)
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url =
            "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/all/batch/update/"
        Timber.d("Url being sent: $url")

        val jsonBody = JSONObject(
            " {\"batchId\": " + batch.batchID + "," +
                    "\"trainingName\": \"" + batch.trainingName + "\"," +
                    "\"trainingType\": \"" + batch.trainingType + "\"," +
                    "\"skillType\": \"" + batch.skillType + "\", " +
                    "\"trainer\": \"" + batch.trainerName + "\"," +
                    "\"coTrainer\": \"" + batch.coTrainerName + "\"," +
                    "\"locationId\": " + batch.locationID + "," +
                    "\"location\": \"" + batch.location + "\"," +
                    "\"startDate\": \"" + DateConverter.postDateString(tempStartDate) + "\"," +
                    "\"endDate\": \"" + DateConverter.postDateString(tempEndDate) + "\"," +
                    "\"goodGrade\": " + batch.goodGrade + "," +
                    "\"passingGrade\": " + batch.passingGrade + "," +
                    "\"weeks\": " + batch.weeks + " }"
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
                Timber.d("Error editing batch: $error")
            }
        )
        queue.add(request)
    }

    fun deleteBatch(batch: Batch) {
        val queue = Volley.newRequestQueue(APIHandler.context)
        val url = "http://caliber-2-dev-alb-315997072.us-east-1.elb.amazonaws.com/batch/all/batch/delete/" + batch.batchID
        Timber.d("Url being sent: $url")
//        val jsonBody = JSONObject("")
//        Timber.d("Request being sent: ${jsonBody.toString()}")
        val request = JsonObjectRequest((
            Request.Method.DELETE),
            url,
            null,
            Response.Listener { response ->
                Timber.d("Response: $response")
                try {
                } catch (e: Exception) {
                    Timber.d("error with live data ${e.toString()}")
                }
            },
            Response.ErrorListener { error ->
                Timber.d("Error deleting batch: %s", error.toString())
            }
        )
        queue.add(request)
    }

}